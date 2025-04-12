package eric.bitria.hexon.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.view.enums.GameActions
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.utils.BoardBuilder
import eric.bitria.hexon.view.utils.CardType
import eric.bitria.hexon.view.utils.ClickHandler
import eric.bitria.hexon.view.utils.ClickHandler.NoParam
import eric.bitria.hexon.view.utils.ClickHandler.None
import eric.bitria.hexon.view.utils.ClickHandler.OnEdge
import eric.bitria.hexon.view.utils.ClickHandler.OnVertex
import eric.bitria.hexon.view.utils.DeckType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val _board by mutableStateOf(BoardBuilder.createInitialBoard())

    private val _turnManager = TurnManager(
        players = listOf(Player(Color(0xFFFF5722)), Player(Color.Blue)),
        onRotationComplete = ::onRotationComplete
    )

    private var _currentPlayer by mutableStateOf(_turnManager.getCurrentPlayer())

    private val _gameManager = GameManager(
        board = _board,
        currentPlayer = _currentPlayer
    )

    private var _cardClickHandler = mutableStateOf<ClickHandler>(None)
    private var _boardClickHandler = mutableStateOf<ClickHandler>(None)
    private var _dices: Pair<Int, Int> by mutableStateOf(_gameManager.getDices())

    // Board Buttons
    private var _availableVertices by mutableStateOf(emptyList<Vertex>())
    private var _availableEdges by mutableStateOf(emptyList<Edge>())

    // Game State
    private var _gamePhase by mutableStateOf(GamePhase.NONE)

    /// Getters

    val board: Board get() = _board
    val player: Player get() = _currentPlayer
    val phase: GamePhase get() = _gamePhase
    val dices: Pair<Int, Int> get() = _dices

    // Click Handlers
    val cardClickHandler: ClickHandler get() = _cardClickHandler.value
    val boardClickHandler: ClickHandler get() = _boardClickHandler.value

    // Board State
    val availableVertices: List<Vertex> get() = _availableVertices
    val availableEdges: List<Edge> get() = _availableEdges

    init {
        initialSettlementPlacement()
    }

    // Initial Placement Round

    private fun initialSettlementPlacement(){
        _gamePhase = GamePhase.INITIAL_PLACEMENT
        // Set board available vertices
        exposeInitialVertices()
        // Board click handler
        _boardClickHandler.value = OnVertex { vertex ->
            resetExposedVertices()
            _gameManager.placeInitialSettlement(vertex)
            initialRoadPlacement()
        }
    }

    private fun initialRoadPlacement(){
        _gamePhase = GamePhase.INITIAL_PLACEMENT

        // Set board available edges
        exposeEdges()

        _boardClickHandler.value = OnEdge { edge ->
            resetExposedEdges()
            _gameManager.placeInitialRoad(edge)
            endInitialPlacementTurn()
        }
    }

    private fun endInitialPlacementTurn(){
        _gamePhase = GamePhase.INITIAL_PLACEMENT

        // Set next Player
        _turnManager.nextTurn()
        _currentPlayer = _turnManager.getCurrentPlayer()
        _gameManager.setCurrentPlayer(_currentPlayer)

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

        // Set roll dice click
        _cardClickHandler.value = NoParam {
            _gameManager.rollDice()
            _dices = _gameManager.getDices()
            _cardClickHandler.value = None // Reset onclick
            viewModelScope.launch {
                delay(1500)
                startTurn()
            }
        }
    }

    private fun startTurn() {
        _gamePhase = GamePhase.PLAYER_TURN

        _cardClickHandler.value = ClickHandler.OnCard { card ->
            when (card) {
                is CardType.BuildingCard -> handleBuilding(card.building)
                is CardType.ActionCard -> handleAction(card.action)
                is CardType.ResourceCard -> handleResource(card)
            }
        }
    }

    /**
     * Called when a player ends their turn.
     */
    fun endTurn() {
        _gamePhase = GamePhase.END_TURN

        _turnManager.nextTurn()
        _currentPlayer = _turnManager.getCurrentPlayer()
        _gameManager.setCurrentPlayer(_currentPlayer)

        rollDices()
    }

    private var _hasReversedTurnOrder = false

    /**
     * Called when all the players have used their turn.
     */
    private fun onRotationComplete(){

        if (_gamePhase == GamePhase.INITIAL_PLACEMENT && !_hasReversedTurnOrder){
            _hasReversedTurnOrder = true
            _turnManager.reverseTurnOrder()
            return
        }

        // Exit Initial Placement Round
        if (_gamePhase == GamePhase.INITIAL_PLACEMENT && _hasReversedTurnOrder){
            _gamePhase = GamePhase.ROLL_DICE
        }
    }

    // Turn Methods

    private fun handleBuilding(building: Building) {
        when (building) {
            Building.NONE -> {}
            Building.SETTLEMENT -> {
                resetExposedEdges()
                exposeVertices()
                _boardClickHandler.value = OnVertex { vertex ->
                    resetExposedVertices()
                    _gameManager.placeSettlement(vertex)
                }
            }
            Building.ROAD -> {
                resetExposedVertices()
                exposeEdges()
                _boardClickHandler.value = OnEdge { edge ->
                    resetExposedEdges()
                    _gameManager.placeRoad(edge)
                }
            }
            Building.CITY -> TODO()
        }
    }

    private fun handleAction(action: GameActions) {
        when (action) {
            GameActions.OPEN_CLOSE_TRADE -> {
                if(_gamePhase == GamePhase.PLAYER_TURN) {
                    _gamePhase = GamePhase.PLAYER_TRADE
                } else {
                    _gamePhase = GamePhase.PLAYER_TURN
                }
            }
            GameActions.ACCEPT_TRADE -> _gamePhase = GamePhase.PLAYER_TURN
            GameActions.END_TURN -> endTurn()
            else -> {}
        }
    }

    private fun handleResource(card: CardType.ResourceCard) {
        when (card.deck) {
            is DeckType.PlayerDeck -> {
                if (_gamePhase == GamePhase.PLAYER_TRADE) {
                    _currentPlayer.addResource(Resource.BRICK,5)
                }
            }
            is DeckType.SystemDeck -> {
                println("Selected ${card.resource} from System Deck")
            }
        }
    }

    // Helper methods

    private fun resetExposedEdges(){
        _availableEdges = emptyList()
    }

    private fun resetExposedVertices(){
        _availableVertices = emptyList()
    }

    private fun exposeInitialVertices(){
        _availableVertices = _board.getVertices().filter { _board.canPlaceBuilding(it) }
    }

    private fun exposeVertices(){
        _availableVertices = _board.getVertices().filter { _board.canPlaceBuilding(it, _currentPlayer) }
    }

    private fun exposeEdges(){
        _availableEdges = _board.getEdges().filter { _board.canPlaceRoad(it, _currentPlayer) }
    }

}