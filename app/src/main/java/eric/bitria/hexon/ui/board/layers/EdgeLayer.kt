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
import eric.bitria.hexon.ui.utils.geometry.HexConversions
import kotlin.math.atan2

@Composable
fun EdgeLayer(
    edges: Collection<Edge>,
    tileSize: Dp
) {
    val tileSizePx = with(LocalDensity.current) { tileSize.toPx() }
    val strokeWidth = tileSizePx * 0.15f

    Canvas(modifier = Modifier.fillMaxSize()) {
        edges.forEach { edge ->
            val (startCoord, endCoord) = edge.getCoords()
            val start = HexConversions.axialToPixel(startCoord.q, startCoord.r, tileSizePx)
            val end = HexConversions.axialToPixel(endCoord.q, endCoord.r, tileSizePx)

            // Draw road as a line instead of rotated rectangle
            drawLine(
                color = Color.Blue,
                start = start,
                end = end,
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        }
    }
}

private fun calculateEdgeAngle(edgeCoords: Pair<AxialCoord, AxialCoord>, size: Float): Float {
    val (a, b) = edgeCoords
    val start = HexConversions.axialToPixel(a.q, a.r, size)
    val end = HexConversions.axialToPixel(b.q, b.r, size)
    return Math.toDegrees(atan2(end.y - start.y, end.x - start.x).toDouble()).toFloat()
}