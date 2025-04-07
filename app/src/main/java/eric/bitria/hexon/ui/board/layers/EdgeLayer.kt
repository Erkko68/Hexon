package eric.bitria.hexon.ui.board.layers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.utils.geometry.HexConversions
import eric.bitria.hexon.ui.board.LocalTileSize

@Composable
fun EdgeLayer(
    edges: Collection<Edge>,
) {
    val tileSizePx = with(LocalDensity.current) { LocalTileSize.current.toPx() }
    val strokeWidth = tileSizePx * 0.15f

    Canvas(modifier = Modifier.fillMaxSize()) {
        edges.forEach { edge ->
            // Get the two vertices that form this edge
            val vertices = edge.getAdjacentVerticesCoords()
            val startPos = HexConversions.getVertexPosition(vertices[0], tileSizePx)
            val endPos = HexConversions.getVertexPosition(vertices[1], tileSizePx)

            edge.player?.let {
                drawLine(
                    color = it.color,
                    start = startPos,
                    end = endPos,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}