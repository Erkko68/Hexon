package eric.bitria.hexon.ui.board

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
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
    parentWidth: Float, // Parent width in pixels
    parentHeight: Float, // Parent height in pixels
    tileSize: Dp = 50.dp, // Size of the tile (hexagon radius)
    color: Color = Color.Gray, // Default tile color
    modifier: Modifier = Modifier
) {
    // Convert axial coordinates to screen position
    val (x, y) = axialToScreen(q, r, parentWidth, parentHeight, tileSize)

    Canvas(modifier = modifier.size(tileSize * 2)) {
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

        // Draw the hexagon with a border
        drawPath(path, color, style = Stroke(2f))
    }
}

/**
 * Converts axial coordinates (q, r) to screen coordinates (x, y).
 *
 * @param q Axial coordinate q
 * @param r Axial coordinate r
 * @param parentWidth Parent width in pixels
 * @param parentHeight Parent height in pixels
 * @param tileSize Size of the tile (hexagon radius)
 * @return Pair of screen coordinates (x, y)
 */
@Composable
private fun axialToScreen(
    q: Int,
    r: Int,
    parentWidth: Float,
    parentHeight: Float,
    tileSize: Dp
): Pair<Float, Float> {
    val tileSizePx = with(LocalDensity.current) { tileSize.toPx() }

    // Calculate the center of the parent
    val centerX = parentWidth / 2
    val centerY = parentHeight / 2

    // Convert axial coordinates to screen coordinates
    val x = centerX + tileSizePx * (3f / 2 * q)
    val y = centerY + tileSizePx * (sqrt(3f) / 2 * q + sqrt(3f) * r)

    return Pair(x, y)
}