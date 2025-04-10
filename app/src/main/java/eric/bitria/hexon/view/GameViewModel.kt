package eric.bitria.hexon.view

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.view.enums.GameAction
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.utils.BoardBuilder
import kotlin.random.Random

class GameViewModel : ViewModel() {
    // Vals
    val player by mutableStateOf(Player(Color(0xFFFF5722)))
    val board by mutableStateOf(BoardBuilder.createInitialBoard())
    var dice by mutableIntStateOf(0)
    var gamePhase by mutableStateOf(GamePhase.INITIAL_PLACEMENT)
    var gameAction by mutableStateOf(GameAction.IDLE)

    // Derived properties
    val availableVertices by derivedStateOf {
        when (gamePhase) {
            GamePhase.INITIAL_PLACEMENT -> {
                board.getVertices().filter { board.canPlaceBuilding(it) }
            }
            GamePhase.PLAYER_TURN -> {
                when (gameAction) {
                    GameAction.PLACE_SETTLEMENT -> board.getVertices().filter { board.canPlaceBuilding(it, player) }
                    else -> emptyList()
                }
            }
            else -> {
                emptyList()
            }
        }
    }

    val availableEdges by derivedStateOf {
        when (gamePhase) {
            GamePhase.INITIAL_PLACEMENT -> {
                board.getEdges().filter { board.canPlaceRoad(it,player) }
            }
            GamePhase.PLAYER_TURN -> {
                when (gameAction) {
                    GameAction.PLACE_ROAD -> board.getEdges().filter { board.canPlaceRoad(it, player) }
                    else -> emptyList()
                }
            }
            else -> {
                emptyList()
            }
        }
    }

    fun onBoardClick(item: Any) {
        when (gamePhase) {
            GamePhase.INITIAL_PLACEMENT -> {
                when(gameAction){
                    GameAction.PLACE_SETTLEMENT -> placeSettlementClick(item)
                    GameAction.PLACE_ROAD -> handleRoadClick(item)
                    else -> {}
                }
            }
            GamePhase.PLAYER_TURN -> {
                when (gameAction) {
                    GameAction.PLACE_SETTLEMENT -> placeSettlementClick(item)
                    GameAction.PLACE_ROAD -> handleRoadClick(item)
                    else -> {}
                }
            }
            GamePhase.ROLL_DICE -> rollDice()
            else -> {}
        }
    }

    private fun rollDice() {
        val dice1 = Random.nextInt(1, 7)
        val dice2 = Random.nextInt(1, 7)
        dice = dice1 + dice2
        // Give resources
        board.giveResource(dice)
        gamePhase = GamePhase.PLAYER_TURN
    }

    private fun placeSettlementClick(item: Any) {
        if (item !is Vertex) return
        board.placeBuilding(item,Building.SETTLEMENT, player)

        // Give initial resources
        if (gamePhase == GamePhase.INITIAL_PLACEMENT){
            board.givePlacementResources(item)
            gamePhase = GamePhase.PLAYER_TURN
            return
        }

        // Else (not in initial placement) remove resources from deck
        player.removeBuildingResources(Building.SETTLEMENT)
        gamePhase = GamePhase.PLAYER_TURN
    }

    private fun handleRoadClick(item: Any) {
        if (item !is Edge) return
        board.placeRoad(item, player)

        if (gamePhase == GamePhase.INITIAL_PLACEMENT) {
            gamePhase = GamePhase.PLAYER_TURN
            return
        }

        // Else (not in initial placement) remove resources from deck
        player.removeBuildingResources(Building.ROAD)
        gamePhase = GamePhase.PLAYER_TURN
    }
}