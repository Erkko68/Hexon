package eric.bitria.hexon.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.view.enums.GameActions
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.utils.BoardBuilder
import eric.bitria.hexon.view.utils.Bot
import eric.bitria.hexon.view.utils.CardType
import eric.bitria.hexon.view.utils.ClickHandler
import eric.bitria.hexon.view.utils.ClickHandler.NoParam
import eric.bitria.hexon.view.utils.ClickHandler.None
import eric.bitria.hexon.view.utils.ClickHandler.OnEdge
import eric.bitria.hexon.view.utils.ClickHandler.OnVertex
import eric.bitria.hexon.view.utils.DeckType
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private var _board by mutableStateOf(Board(1))
    private var _turnManager: TurnManager = TurnManager(emptyList())
    private var _cardClickHandler = mutableStateOf<ClickHandler>(None)
    private var _boardClickHandler = mutableStateOf<ClickHandler>(None)
    private var _gamePhase by mutableStateOf(GamePhase.NONE)
    private var _availableVertices by mutableStateOf(emptyList<Vertex>())
    private var _availableEdges by mutableStateOf(emptyList<Edge>())

    private var _player by mutableStateOf(Player(Color.Transparent))
    private var _currentPlayer by mutableStateOf(Player(Color.Transparent))
    private var _dice1 by mutableIntStateOf(0)
    private var _dice2 by mutableIntStateOf(0)

    // Timer
    private var timerJob: Job? = null
    private var turnTimeMillis = 30_000L // 30 seconds
    private val _timeLeft = mutableLongStateOf(turnTimeMillis / 1000)

    // Getters
    val board: Board get() = _board
    val player: Player get() = _player
    val phase: GamePhase get() = _gamePhase
    val dices: Pair<Int, Int> get() = Pair(_dice1, _dice2)
    val timeLeft: Long get() = _timeLeft.longValue
    val cardClickHandler: ClickHandler get() = _cardClickHandler.value
    val boardClickHandler: ClickHandler get() = _boardClickHandler.value

    // Board State
    val availableVertices: List<Vertex> get() = _availableVertices
    val availableEdges: List<Edge> get() = _availableEdges

    // Initial Placement Round
    fun startNewGame(player: Player, players: List<Player>, timer: Long = 30_000L) {
        _board = BoardBuilder.createInitialBoard()
        _turnManager = TurnManager(players, onRotationComplete = ::onRotationComplete)
        _currentPlayer = _turnManager.getCurrentPlayer()
        _player = player // Set non bot player
        turnTimeMillis = timer

        _availableVertices = emptyList()
        _availableEdges = emptyList()
        _cardClickHandler.value = None
        _boardClickHandler.value = None

        initialSettlementPlacement()
    }

    private fun initialSettlementPlacement(){
        _gamePhase = GamePhase.INITIAL_PLACEMENT

        if(_currentPlayer.isBot){
            viewModelScope.launch {
                delay(2000)
                Bot.initialSettlementPlacement(_board,_currentPlayer)
                initialRoadPlacement()
            }
        } else {

            // Set board available vertices
            exposeInitialVertices()
            // Board click handler
            _boardClickHandler.value = OnVertex { vertex ->
                resetExposedVertices()
                GameManager.placeInitialSettlement(_board, _currentPlayer, vertex)
                initialRoadPlacement()
            }
        }
    }

    private fun initialRoadPlacement(){
        _gamePhase = GamePhase.INITIAL_PLACEMENT

        if(_currentPlayer.isBot){
            viewModelScope.launch {
                delay(2000)
                Bot.placeRoad(_board,_currentPlayer)
                endInitialPlacementTurn()
            }
        } else {

            // Set board available edges
            exposeEdges()

            _boardClickHandler.value = OnEdge { edge ->
                resetExposedEdges()
                GameManager.placeInitialRoad(_board, _currentPlayer, edge)
                endInitialPlacementTurn()
            }
        }
    }

    private fun endInitialPlacementTurn(){
        _gamePhase = GamePhase.INITIAL_PLACEMENT

        _currentPlayer = _turnManager.nextTurn()

        // If nextTurn triggers end round switch to normal game execution
        if(_gamePhase == GamePhase.ROLL_DICE){
            rollDices()
        } else {
            initialSettlementPlacement()
        }
    }

    // Normal Game Rounds

    private fun rollDices() {
        _gamePhase = GamePhase.ROLL_DICE

        _currentPlayer = _turnManager.getCurrentPlayer()
        // Rest click handlers
        _boardClickHandler.value = None
        _cardClickHandler.value = None

        if(_currentPlayer.isBot){
            viewModelScope.launch {
                delay(2000)
                updateDice(GameManager.rollDice(_board))
                delay(1000)
                startTurn()
            }
        } else {
            resetTurnTimer {
                updateDice(GameManager.rollDice(_board))
                viewModelScope.launch {
                    delay(1000)
                    startTurn()
                }
            }

            // Set roll dice click
            _cardClickHandler.value = NoParam {
                updateDice(GameManager.rollDice(_board))
                _cardClickHandler.value = None // Reset onclick
                stopTimer()
                viewModelScope.launch {
                    delay(1000)
                    startTurn()
                }
            }
        }
    }

    private fun startTurn() {
        _gamePhase = GamePhase.PLAYER_TURN

        if(_currentPlayer.isBot){
            _gamePhase = GamePhase.BOT_TURN
            Bot.placeRoad(_board,_currentPlayer)
            Bot.placeSettlement(_board,_currentPlayer)
            viewModelScope.launch {
                delay(2000)
                endTurn()
            }
        } else {
            resetTurnTimer {
                endTurn()
            }

            _cardClickHandler.value = ClickHandler.OnCard { card ->
                when (card) {
                    is CardType.BuildingCard -> handleBuilding(card.building)
                    is CardType.ActionCard -> handleAction(card.action)
                    is CardType.ResourceCard -> handleResource(card)
                }
            }
        }
    }

    fun endTurn() {
        // Cleanup
        stopTimer()
        _currentPlayer.cancelTrade()
        _boardClickHandler.value = None
        _cardClickHandler.value = None
        resetExposedEdges()
        resetExposedVertices()

        // Next Phase
        _gamePhase = GamePhase.END_TURN

        _currentPlayer = _turnManager.nextTurn()

        rollDices()
    }

    // Turn Methods

    private fun onRotationComplete(){

        if (_gamePhase == GamePhase.INITIAL_PLACEMENT && !_turnManager.hasReversedTurnOrder()){
            _turnManager.reverseTurnOrder()
            return
        }

        // Exit Initial Placement Round
        if (_gamePhase == GamePhase.INITIAL_PLACEMENT && _turnManager.hasReversedTurnOrder()){
            _gamePhase = GamePhase.ROLL_DICE
        }
    }

    private fun handleBuilding(building: Building) {
        when (building) {
            Building.NONE -> {}
            Building.SETTLEMENT -> {
                resetExposedEdges()
                exposeVertices()
                _boardClickHandler.value = OnVertex { vertex ->
                    resetExposedVertices()
                    GameManager.placeSettlement(_board, _currentPlayer, vertex)
                }
            }
            Building.ROAD -> {
                resetExposedVertices()
                exposeEdges()
                _boardClickHandler.value = OnEdge { edge ->
                    resetExposedEdges()
                    GameManager.placeRoad(_board, _currentPlayer, edge)
                }
            }
            Building.CITY -> {
                resetExposedEdges()
                exposeUpgradeableVertices()
                _boardClickHandler.value = OnVertex { vertex ->
                    resetExposedVertices()
                    GameManager.placeCity(_board, _currentPlayer, vertex)
                }
            }
        }
    }

    private fun handleAction(action: GameActions) {
        resetExposedEdges()
        resetExposedVertices()
        when (action) {
            GameActions.OPEN_TRADE -> {
                _gamePhase = GamePhase.PLAYER_TRADE
            }
            GameActions.CLOSE_TRADE -> {
                _currentPlayer.cancelTrade()
                _gamePhase = GamePhase.PLAYER_TURN
            }
            GameActions.ACCEPT_TRADE -> {
                _currentPlayer.acceptTrade()
                _gamePhase = GamePhase.PLAYER_TURN
            }
            GameActions.END_TURN -> {
                endTurn()
            }
        }
    }

    private fun handleResource(card: CardType.ResourceCard) {
        when (card.deck) {
            is DeckType.PlayerDeck -> {
                if (_gamePhase == GamePhase.PLAYER_TRADE) {
                    _currentPlayer.addTradingResource(card.resource)
                }
            }
            is DeckType.SystemDeck -> {
                _currentPlayer.selectTradingResource(card.resource)
            }
        }
    }

    // State Methods

    private fun updateDice(dices: Pair<Int, Int>){
        _dice1 = dices.first
        _dice2 = dices.second
    }

    private fun resetExposedEdges(){ _availableEdges = emptyList() }

    private fun resetExposedVertices(){ _availableVertices = emptyList() }

    private fun exposeInitialVertices(){ _availableVertices = _board.getVertices().filter { _board.canPlaceBuilding(it) } }

    private fun exposeVertices(){ _availableVertices = _board.getVertices().filter { _board.canPlaceBuilding(it, _currentPlayer) } }

    private fun exposeUpgradeableVertices(){ _availableVertices = _board.getVertices().filter { _board.canUpgradeBuilding(it, _currentPlayer) } }

    private fun exposeEdges(){ _availableEdges = _board.getEdges().filter { _board.canPlaceRoad(it, _currentPlayer) } }

    // Timer

    private fun resetTurnTimer(onTimeExpired: () -> Unit) {
        timerJob?.cancel() // cancel if running

        timerJob = viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            while (isActive) {
                val elapsed = System.currentTimeMillis() - startTime
                val remaining = turnTimeMillis - elapsed

                if (remaining <= 0) {
                    _timeLeft.longValue = 0
                    onTimeExpired()
                    break
                } else {
                    _timeLeft.longValue = remaining / 1000
                    delay(1000)
                }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }
}