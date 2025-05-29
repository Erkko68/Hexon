package eric.bitria.hexon.view.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.view.utils.BoardBuilder

class BoardManager {
    var board by mutableStateOf(Board(1)) // Initial default board
        private set
    var availableVertices by mutableStateOf(emptyList<Vertex>())
        private set
    var availableEdges by mutableStateOf(emptyList<Edge>())
        private set

    fun initializeBoard() {
        board = BoardBuilder.createInitialBoard()
        clearHighlights()
    }

    fun highlightInitialSettlementVertices() {
        availableVertices = board.getVertices().filter { board.canPlaceBuilding(it) }
        availableEdges = emptyList()
    }

    fun highlightSettlementVertices(player: Player) {
        availableVertices = board.getVertices().filter { board.canPlaceBuilding(it, player) }
        availableEdges = emptyList()
    }

    fun highlightCityUpgradableVertices(player: Player) {
        availableVertices = board.getVertices().filter { board.canUpgradeBuilding(it, player) }
        availableEdges = emptyList()
    }

    fun highlightRoadEdges(player: Player) {
        availableEdges = board.getEdges().filter { board.canPlaceRoad(it, player) }
        availableVertices = emptyList()
    }

    fun highlightRoadEdgesForInitialPlacement(player: Player) {
        availableEdges = board.getEdges().filter { board.canPlaceRoad(it, player) } // Simplified
        availableVertices = emptyList()
    }

    fun clearHighlights() {
        availableVertices = emptyList()
        availableEdges = emptyList()
    }
}