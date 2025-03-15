package eric.bitria.hexon.ui.board.layers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.player.Player

@Composable
fun AvailablePositionsLayer(
    board: Board,
    tileSize: Dp
) {
    val availableVertices = remember(board) {
        board.getVertices().filter { board.canPlaceBuilding(it) }
    }

    val availableEdges = remember(board) {
        board.getEdges().filter { board.canPlaceRoad(it, Player()) }
    }

    VertexLayer(availableVertices, tileSize)
    EdgeLayer(availableEdges, tileSize)
}