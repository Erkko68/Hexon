package eric.bitria.hexon.ui.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.board.layers.AvailablePositionsLayer
import eric.bitria.hexon.ui.board.layers.EdgeLayer
import eric.bitria.hexon.ui.board.layers.HexagonalTileLayer
import eric.bitria.hexon.ui.board.layers.VertexLayer
import eric.bitria.hexon.ui.utils.modifier.ZoomState

@Composable
fun BoardRenderer(
    board: Board,
    player: Player,
    zoomState: ZoomState,
    tileSize: Dp = 32.dp
) {
    val scaledTileSize = remember(zoomState.zoomLevel) {
        tileSize * zoomState.zoomLevel
    }

    Box(modifier = Modifier.offset {
            IntOffset(zoomState.offsetX.toInt(), zoomState.offsetY.toInt())
        }
    ) {
        // Base Hexagonal Tiles Layer
        HexagonalTileLayer(board, scaledTileSize)

        // Existing Buildings Layer
        VertexLayer(board.getVertices().filter { it.hasBuilding() }, scaledTileSize)

        // Existing Roads Layer
        EdgeLayer(board.getEdges().filter { it.hasBuilding() }, scaledTileSize)

        // Available Positions Layer
        AvailablePositionsLayer(
            board = board,
            tileSize = scaledTileSize,
            player = player
        )
    }
}