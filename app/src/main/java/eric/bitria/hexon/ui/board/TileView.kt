package eric.bitria.hexon.ui.board

import eric.bitria.hexon.ui.utils.function.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.Dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

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
        val path = createHexagon(x, y, radius)

        // Draw the filled hexagon
        drawPath(path, color, style = Fill)
    }
}

private fun createHexagon(x: Float, y: Float, radius: Float): Path {
    return Path().apply {
        for (i in 0 until 6) {
            // Calculate the angle for a pointy-oriented hexagon
            val angle = 2 * Math.PI / 6 * i + Math.PI / 6 // Add 30 degrees (PI/6 radians)
            val hexX = x + radius * cos(angle.toFloat())
            val hexY = y + radius * sin(angle.toFloat())
            if (i == 0) moveTo(hexX, hexY) else lineTo(hexX, hexY)
        }
        close()
    }
}