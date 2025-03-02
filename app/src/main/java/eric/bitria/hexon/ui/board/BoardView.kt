package eric.bitria.hexon.ui.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import eric.bitria.hexon.src.board.Board

@Composable
fun BoardView(
    board: Board,
    modifier: Modifier = Modifier
) {
    var parentSize by remember { mutableStateOf(Size.Zero) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { size ->
                parentSize = size.toSize()
            }
    ) {
        // Render all tiles
        board.tiles.forEach { (coordinates, tile) ->
            val (q, r) = coordinates
            TileView(
                q = q,
                r = r,
                parentWidth = parentSize.width,
                parentHeight = parentSize.height,
                tileSize = 20.dp,
                color = tile.type.color,
                modifier = Modifier
            )
        }
    }
}