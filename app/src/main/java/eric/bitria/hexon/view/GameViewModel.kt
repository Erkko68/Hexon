package eric.bitria.hexon.view

import androidx.compose.runtime.derivedStateOf
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
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.view.enums.GameAction
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.utils.BoardBuilder
import eric.bitria.hexon.view.utils.ClickHandler
import eric.bitria.hexon.view.utils.ClickHandler.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

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

    private var _clickHandler = mutableStateOf<ClickHandler>(None)
    private var _dices: Pair<Int, Int> by mutableStateOf(_gameManager.getDices())

    // Game State
    private var _gamePhase by mutableStateOf(GamePhase.NONE)

    // Getters
    val board: Board get() = _board
    val player: Player get() = _currentPlayer
    val clickHandler: ClickHandler get() = _clickHandler.value
    val phase: GamePhase get() = _gamePhase
    val dices: Pair<Int, Int> get() = _dices

    // Derived properties
    val availableVertices by derivedStateOf {
        when (_gamePhase) {
            GamePhase.INITIAL_PLACEMENT -> {
                if (_currentPlayer.getAction() == GameAction.PLACE_SETTLEMENT) {
                    _board.getVertices().filter { _board.canPlaceBuilding(it) }
                } else {
                    emptyList()
                }
            }
            GamePhase.PLAYER_TURN -> {
                when (_currentPlayer.getAction()) {
                    GameAction.PLACE_SETTLEMENT -> {
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
                if (_currentPlayer.getAction() == GameAction.PLACE_ROAD) {
                    _board.getEdges().filter { _board.canPlaceRoad(it, _currentPlayer) }
                } else {
                    emptyList()
                }
            }
            GamePhase.PLAYER_TURN -> {
                when (_currentPlayer.getAction()) {
                    GameAction.PLACE_ROAD -> {
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
        _currentPlayer.setAction(GameAction.PLACE_SETTLEMENT)

        _clickHandler.value = OnVertex { vertex ->
            _gameManager.placeInitialSettlement(vertex)
            initialRoadPlacement()
        }
    }

    private fun initialRoadPlacement(){
        _gamePhase = GamePhase.INITIAL_PLACEMENT
        _currentPlayer.setAction(GameAction.PLACE_ROAD)
        _clickHandler.value = OnEdge { edge ->
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
        _currentPlayer.setAction(GameAction.IDLE)
        _currentPlayer = _turnManager.getCurrentPlayer()
        _clickHandler.value = NoParam {
            _gameManager.rollDice()
            _dices = _gameManager.getDices()
            _clickHandler.value = None // Reset onclick
            viewModelScope.launch {
                delay(3000)
                startTurn()
            }
        }
    }

    private fun startTurn() {
        _gamePhase = GamePhase.PLAYER_TURN
        _clickHandler.value = OnBuilding { building ->
            when (building) {
                Building.SETTLEMENT ->{
                    _currentPlayer.setAction(GameAction.PLACE_SETTLEMENT)
                    _clickHandler.value = OnVertex { vertex ->
                        _gameManager.placeSettlement(vertex)
                        _currentPlayer.setAction(GameAction.IDLE)
                        endTurn()
                    }
                }
                Building.ROAD -> {
                    _currentPlayer.setAction(GameAction.PLACE_ROAD)
                    _clickHandler.value = OnEdge { edge ->
                        _gameManager.placeRoad(edge)
                        _currentPlayer.setAction(GameAction.IDLE)
                        endTurn()
                    }
                }
                Building.CITY -> {
                    _currentPlayer.setAction(GameAction.PLACE_CITY)
                    // TODO
                    _currentPlayer.setAction(GameAction.IDLE)
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