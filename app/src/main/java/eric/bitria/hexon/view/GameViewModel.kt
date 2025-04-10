package eric.bitria.hexon.view

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.view.enums.GameAction
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.utils.BoardBuilder

class GameViewModel : ViewModel() {

    private val _board by mutableStateOf(BoardBuilder.createInitialBoard())
    private val _turnManager = TurnManager(listOf(Player(Color(0xFFFF5722)), Player(Color.Blue)))
    private var _currentPlayer by mutableStateOf(_turnManager.getCurrentPlayer())
    private val _gameManager = GameManager(_board, _currentPlayer)
    private var _onClick = mutableStateOf<((Any) -> Unit)>({
        it: Any -> _gameManager.placeInitialSettlement(it as Vertex)
        _currentPlayer.setAction(GameAction.PLACE_ROAD)
        initialRoadPlacement()
    })
    private var _onCardSelect: (Any) -> Unit = ::onCardSelect

    // Game State
    private var _gamePhase by mutableStateOf(GamePhase.INITIAL_PLACEMENT)

    // Getters
    val board: Board get() = _board
    val player: Player get() = _currentPlayer
    val onClick: (Any) -> Unit get() = _onClick.value
    val onCardSelect: (Any) -> Unit get() = _onCardSelect

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

    // Player Card Selection
    fun onCardSelect(card: Any){
        if(card is Building){
            when(card){
                Building.SETTLEMENT -> _currentPlayer.setAction(GameAction.PLACE_SETTLEMENT)
                Building.ROAD -> _currentPlayer.setAction(GameAction.PLACE_ROAD)
                Building.CITY -> _currentPlayer.setAction(GameAction.PLACE_CITY)
                else -> {}
            }
        }
    }

    fun initialRoadPlacement(){
        _gamePhase = GamePhase.INITIAL_PLACEMENT
        _onClick.value = {
            it: Any -> _gameManager.placeInitialRoad(it as Edge)
            _currentPlayer.setAction(GameAction.IDLE)
            rollDices()
        }
    }

    fun rollDices() {
        _gamePhase = GamePhase.ROLL_DICE
        _currentPlayer = _turnManager.getCurrentPlayer()
        _onClick.value = {
            _: Any -> _gameManager.rollDice()
            startTurn()
        }
    }

    fun startTurn() {
        _gamePhase = GamePhase.PLAYER_TURN
        _onClick.value = { element : Any ->
            when (_currentPlayer.getAction()) {
                GameAction.IDLE -> {}
                GameAction.PLACE_CITY -> {
                    _currentPlayer.setAction(GameAction.IDLE)
                    endTurn()
                }
                GameAction.PLACE_SETTLEMENT -> {
                    _gameManager.placeSettlement(element as Vertex)
                    _currentPlayer.setAction(GameAction.IDLE)
                    endTurn()
                }
                GameAction.PLACE_ROAD -> {
                    _gameManager.placeRoad(element as Edge)
                    _currentPlayer.setAction(GameAction.IDLE)
                    endTurn()
                }
            }
        }
    }


    fun endTurn() {
        _gamePhase = GamePhase.NEXT_TURN
        _turnManager.nextTurn()
        rollDices()
    }

}