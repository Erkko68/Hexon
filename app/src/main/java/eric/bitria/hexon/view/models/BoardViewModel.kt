package eric.bitria.hexon.view.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.view.utils.BoardBuilder

class BoardViewModel {
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

    fun updateBoardSnapshot() {
        // If Board object is mutable and changes are made directly,
        // this function can be called to trigger recomposition for UI elements observing 'board'.
        // A common way is to create a new instance or update a version counter.
        // For simplicity, if GameManager mutates the board directly, ensure UI observes specific board properties
        // or consider Board returning new instances on modification.
        // If Board is immutable and GameManager returns new Board instances:
        // board = newBoardInstance
        board = board // This line itself might not be enough if Board is mutable and changed internally.
        // A better approach for mutable objects is to create a new wrapper or use a snapshot system.
        // For this example, we'll assume direct mutations trigger recomposition where board is used.
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
        // Assuming initial road placement might have slightly different rules or available edges
        // For now, let's assume it's the same as regular road placement for simplicity.
        // If different, the logic for board.getEdges().filter { board.canPlaceInitialRoad(it, player) } would be here.
        availableEdges = board.getEdges().filter { board.canPlaceRoad(it, player) } // Simplified
        availableVertices = emptyList()
    }

    fun clearHighlights() {
        availableVertices = emptyList()
        availableEdges = emptyList()
    }
}