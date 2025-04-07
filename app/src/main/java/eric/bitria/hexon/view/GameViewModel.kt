package eric.bitria.hexon.view

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import eric.bitria.hexon.src.board.BoardBuilder
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.player.Player

class GameViewModel : ViewModel() {
    // Existing properties
    val player by mutableStateOf(Player(Color(0xFFFF5722)))
    val board by mutableStateOf(BoardBuilder.createInitialBoard())
    var gamePhase by mutableStateOf(GamePhase.PLACE_SETTLEMENT)

    // Derived properties
    val availableVertices by derivedStateOf {
        board.getVertices().filter { board.canPlaceBuilding(it) }
    }

    val availableEdges by derivedStateOf {
        board.getEdges().filter { board.canPlaceRoad(it, player) }
    }

    // Define click handlers as mutable state
    fun onBoardClick(item: Any) {
        when {
            item is Vertex && gamePhase == GamePhase.PLACE_SETTLEMENT ->
                handleSettlementClick(item)

            item is Edge && gamePhase == GamePhase.PLACE_ROAD ->
                handleRoadClick(item)
        }
    }

    private fun handleSettlementClick(item: Any) {
        if (item !is Vertex) return
        board.placeBuilding(item,Building.SETTLEMENT, player)
        board.givePlacementResources(item)
        //player.deck.removeBuildingResources(Building.SETTLEMENT)
        gamePhase = GamePhase.PLACE_ROAD
    }

    private fun handleRoadClick(item: Any) {
        if (item !is Edge) return
        board.placeRoad(item, player)
        //player.deck.removeBuildingResources(Building.ROAD)
        gamePhase = GamePhase.PLACE_SETTLEMENT
    }
}