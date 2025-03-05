package eric.bitria.hexon.ui.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.ui.utils.modifier.ZoomState
import eric.bitria.hexon.ui.utils.modifier.zoomable

@Composable
fun BoardView(
    board: Board,
    modifier: Modifier = Modifier
) {
    val zoomState = remember { ZoomState() }
    var parentSize by remember { mutableStateOf(Size.Zero) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { parentSize = it.toSize() }
            .zoomable(zoomState) // Use the new zoomable modifier
    ) {
        LaunchedEffect(parentSize) {
            val centerX = parentSize.width / 2f
            val centerY = parentSize.height / 2f
            zoomState.offsetX = centerX
            zoomState.offsetY = centerY
        }
        RenderTiles(board, zoomState)
    }
}

@Composable
fun RenderTiles(
    board: Board,
    zoomState: ZoomState
) {
    Box(
        modifier = Modifier.offset {
            IntOffset(zoomState.offsetX.toInt(), zoomState.offsetY.toInt())
        }
    ) {
        board.tiles.forEach { (coordinates, tile) ->
            val (q, r) = coordinates
            TileView(
                q = q,
                r = r,
                tileSize = (20.dp * zoomState.zoomLevel),
                color = tile.type.color
            )
        }
    }
}