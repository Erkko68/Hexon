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
import kotlin.random.Random

class GameViewModel : ViewModel() {
    // Vals
    val player by mutableStateOf(Player(Color(0xFFFF5722)))
    val board by mutableStateOf(BoardBuilder.createInitialBoard())
    var dice by mutableIntStateOf(0)
    var gamePhase by mutableStateOf(GamePhase.INITIAL_SETTLEMENT_PLACEMENT)

    // Derived properties
    val availableVertices by derivedStateOf {
        when (gamePhase) {
            GamePhase.INITIAL_SETTLEMENT_PLACEMENT -> {
                board.getVertices().filter { board.canPlaceBuilding(it) }
            }
            GamePhase.PLACE_SETTLEMENT -> {
                board.getVertices().filter { board.canPlaceBuilding(it, player) }
            }
            else -> {
                emptyList()
            }
        }
    }
    val availableEdges by derivedStateOf {
        when (gamePhase) {
            GamePhase.PLACE_ROAD, GamePhase.INITIAL_ROAD_PLACEMENT -> {
                board.getEdges().filter { board.canPlaceRoad(it,player) }
            }
            else -> {
                emptyList()
            }
        }
    }

    fun onBoardClick(item: Any) {
        when (gamePhase) {
            GamePhase.INITIAL_SETTLEMENT_PLACEMENT, GamePhase.PLACE_SETTLEMENT -> placeSettlementClick(item)
            GamePhase.PLACE_ROAD, GamePhase.INITIAL_ROAD_PLACEMENT -> handleRoadClick(item)
            GamePhase.ROLL_DICE -> rollDice()
            GamePhase.IDLE -> TODO()
        }
    }

    private fun rollDice() {
        val dice1 = Random.nextInt(1, 7)
        val dice2 = Random.nextInt(1, 7)
        dice = dice1 + dice2
        // Give resources
        board.giveResource(dice)
        gamePhase = GamePhase.IDLE
    }

    private fun placeSettlementClick(item: Any) {
        if (item !is Vertex) return
        board.placeBuilding(item,Building.SETTLEMENT, player)

        // Give initial resources
        if (gamePhase == GamePhase.INITIAL_SETTLEMENT_PLACEMENT){
            board.givePlacementResources(item)
            gamePhase = GamePhase.INITIAL_ROAD_PLACEMENT
            return
        }

        // Else (not in initial placement) remove resources from deck
        player.removeBuildingResources(Building.SETTLEMENT)
        gamePhase = GamePhase.IDLE
    }

    private fun handleRoadClick(item: Any) {
        if (item !is Edge) return
        board.placeRoad(item, player)

        if (gamePhase == GamePhase.INITIAL_ROAD_PLACEMENT) {
            gamePhase = GamePhase.INITIAL_SETTLEMENT_PLACEMENT
            return
        }

        // Else (not in initial placement) remove resources from deck
        player.removeBuildingResources(Building.ROAD)
        gamePhase = GamePhase.IDLE
    }
}