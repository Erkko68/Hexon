package eric.bitria.hexon.ui.board

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun TileView(
    q: Int, // Axial coordinate q
    r: Int, // Axial coordinate r
    tileSize: Dp,
    color: Color,
    modifier: Modifier = Modifier
) {
    // Convert axial coordinates to screen position
    val (x, y) = axialToScreen(q, r, tileSize)

    Canvas(
        modifier = modifier.size(tileSize * 2)
    ) {
        val radius = tileSize.toPx()

        // Draw the hexagon centered at (x, y)
        val path = Path().apply {
            for (i in 0 until 6) {
                val angle = 2 * Math.PI / 6 * i
                val hexX = x + radius * cos(angle.toFloat())
                val hexY = y + radius * sin(angle.toFloat())
                if (i == 0) moveTo(hexX, hexY) else lineTo(hexX, hexY)
            }
            close()
        }

        // Draw the filled hexagon
        drawPath(path, color, style = Fill)
    }
}

/**
 * Converts axial coordinates (q, r) to screen coordinates (x, y).
 *
 * @param q Axial coordinate q
 * @param r Axial coordinate r
 * @param tileSize Size of the tile (hexagon radius)
 * @return Pair of screen coordinates (x, y)
 */
@Composable
private fun axialToScreen(
    q: Int,
    r: Int,
    tileSize: Dp
): Pair<Float, Float> {
    val tileSizePx = with(LocalDensity.current) { tileSize.toPx() }

    // Convert axial coordinates to screen coordinates
    val x = tileSizePx * (3f / 2 * q)
    val y = tileSizePx * (sqrt(3f) / 2 * q + sqrt(3f) * r)

    return Pair(x, y)
}