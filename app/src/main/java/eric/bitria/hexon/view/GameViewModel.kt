package eric.bitria.hexon.view

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.view.enums.GameActions
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.utils.BoardBuilder
import eric.bitria.hexon.view.utils.ClickHandler
import eric.bitria.hexon.view.utils.ClickHandler.*
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

    // Game State
    private var _gamePhase by mutableStateOf(GamePhase.NONE)

    // Getters
    val board: Board get() = _board
    val player: Player get() = _currentPlayer
    val cardClickHandler: ClickHandler get() = _cardClickHandler.value
    val boardClickHandler: ClickHandler get() = _boardClickHandler.value
    val phase: GamePhase get() = _gamePhase
    val dices: Pair<Int, Int> get() = _dices

    // Derived properties
    val availableVertices by derivedStateOf {
        when (_gamePhase) {
            GamePhase.INITIAL_PLACEMENT -> {
                if (_currentPlayer.getAction() == GameActions.PLACE_SETTLEMENT) {
                    _board.getVertices().filter { _board.canPlaceBuilding(it) }
                } else {
                    emptyList()
                }
            }
            GamePhase.PLAYER_TURN -> {
                when (_currentPlayer.getAction()) {
                    GameActions.PLACE_SETTLEMENT -> {
                        _board.getVertices().filter { _board.canPlaceBuilding(it, _currentPlayer) }
                    }
                    else -> emptyList()
                }
            }
            else -> emptyList()
        }
    }

    val availableEdges by derivedStateOf {
        when (_gamePhase) {
            GamePhase.INITIAL_PLACEMENT -> {
                if (_currentPlayer.getAction() == GameActions.PLACE_ROAD) {
                    _board.getEdges().filter { _board.canPlaceRoad(it, _currentPlayer) }
                } else {
                    emptyList()
                }
            }
            GamePhase.PLAYER_TURN -> {
                when (_currentPlayer.getAction()) {
                    GameActions.PLACE_ROAD -> {
                        _board.getEdges().filter { _board.canPlaceRoad(it, _currentPlayer) }
                    }
                    else -> emptyList()
                }
            }
            else -> emptyList()
        }
    }

    init {
        initialSettlementPlacement()
    }

    // Initial Placement Round

    private fun initialSettlementPlacement(){
        _gamePhase = GamePhase.INITIAL_PLACEMENT
        _currentPlayer.setAction(GameActions.PLACE_SETTLEMENT)

        _boardClickHandler.value = OnVertex { vertex ->
            _gameManager.placeInitialSettlement(vertex)
            initialRoadPlacement()
        }
    }

    private fun initialRoadPlacement(){
        _gamePhase = GamePhase.INITIAL_PLACEMENT
        _currentPlayer.setAction(GameActions.PLACE_ROAD)
        _boardClickHandler.value = OnEdge { edge ->
            _gameManager.placeInitialRoad(edge)
            endInitialPlacementTurn()
        }
    }

    private fun endInitialPlacementTurn(){
        _gamePhase = GamePhase.INITIAL_PLACEMENT

        _turnManager.nextTurn()

        // Set next Player
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
        _currentPlayer.setAction(GameActions.IDLE)
        _currentPlayer = _turnManager.getCurrentPlayer()
        _boardClickHandler.value = None
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
        _cardClickHandler.value = OnAction { action ->
            when (action) {
                GameActions.PLACE_SETTLEMENT ->{
                    _currentPlayer.setAction(GameActions.PLACE_SETTLEMENT)
                    _boardClickHandler.value = OnVertex { vertex ->
                        _gameManager.placeSettlement(vertex)
                        _currentPlayer.setAction(GameActions.IDLE)
                    }
                }
                GameActions.PLACE_ROAD -> {
                    _currentPlayer.setAction(GameActions.PLACE_ROAD)
                    _boardClickHandler.value = OnEdge { edge ->
                        _gameManager.placeRoad(edge)
                        _currentPlayer.setAction(GameActions.IDLE)
                    }
                }
                GameActions.PLACE_CITY -> {
                    _currentPlayer.setAction(GameActions.PLACE_CITY)
                    // TODO
                    _currentPlayer.setAction(GameActions.IDLE)
                }
                GameActions.END_TURN -> {
                    endTurn()
                }
                else -> {}
            }
        }
    }

    /**
     * Called when a player ends their turn.
     */
    fun endTurn() {
        _gamePhase = GamePhase.NEXT_TURN

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

}