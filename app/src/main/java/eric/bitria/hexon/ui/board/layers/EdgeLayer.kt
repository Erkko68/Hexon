package eric.bitria.hexon.ui.board.layers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.utils.geometry.HexConversions
import kotlin.math.atan2

@Composable
fun EdgeLayer(
    edges: Collection<Edge>,
    tileSize: Dp,
    player: Player
) {
    val tileSizePx = with(LocalDensity.current) { tileSize.toPx() }
    val strokeWidth = tileSizePx * 0.15f

    Canvas(modifier = Modifier.fillMaxSize()) {
        edges.forEach { edge ->
            // Get the two vertices that form this edge
            val vertices = edge.getAdjacentVerticesCoords()
            val startPos = HexConversions.getVertexPosition(vertices[0], tileSizePx)
            val endPos = HexConversions.getVertexPosition(vertices[1], tileSizePx)

            drawLine(
                color = player.color,
                start = startPos,
                end = endPos,
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        }
    }
}