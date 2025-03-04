package eric.bitria.hexon.ui.board

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import eric.bitria.hexon.src.board.Board

@Composable
fun BoardView(
    board: Board,
    modifier: Modifier = Modifier
) {
    var parentSize by remember { mutableStateOf(Size.Zero) }
    var zoomLevel by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val minZoom = 0.5f
    val maxZoom = 3f

    val transformationModifier = Modifier.pointerInput(Unit) {
        detectTransformGestures { _, pan, zoom, _ ->
            zoomLevel = (zoomLevel * zoom).coerceIn(minZoom, maxZoom)
            offsetX += pan.x
            offsetY += pan.y
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { size ->
                parentSize = size.toSize()
            }
            .then(transformationModifier)
    ) {
        // Render all tiles with adjusted size and position
        board.tiles.forEach { (coordinates, tile) ->
            val (q, r) = coordinates
            TileView(
                q = q,
                r = r,
                parentWidth = parentSize.width,
                parentHeight = parentSize.height,
                tileSize = (20.dp * zoomLevel), // Adjusted tile size
                color = tile.type.color,
                modifier = Modifier
                    .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) } // Move board with gestures
            )
        }
    }
}
