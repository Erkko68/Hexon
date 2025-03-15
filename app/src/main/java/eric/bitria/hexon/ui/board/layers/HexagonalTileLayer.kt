package eric.bitria.hexon.ui.board.layers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.ui.utils.geometry.HexConversions
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HexagonalTileLayer(
    board: Board,
    tileSize: Dp
) {
    val tileSizePx = with(LocalDensity.current) { tileSize.toPx() }

    Canvas(modifier = Modifier.fillMaxSize()) {
        board.getTiles().forEach { tile ->
            val center = HexConversions.axialToPixel(tile.coords.q, tile.coords.r, tileSizePx)
            drawHexagon(center, tileSizePx, tile.resource.color)
        }
    }
}


private fun DrawScope.drawHexagon(center: Offset, size: Float, color: Color) {
    val path = Path().apply {
        (0 until 6).forEach { i ->
            val angle = 60f * i - 30f
            val x = center.x + size * cos(Math.toRadians(angle.toDouble())).toFloat()
            val y = center.y + size * sin(Math.toRadians(angle.toDouble())).toFloat()
            if (i == 0) moveTo(x, y) else lineTo(x, y)
        }
        close()
    }
    drawPath(path, color, style = Fill)
}