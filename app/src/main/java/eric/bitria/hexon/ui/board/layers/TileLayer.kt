package eric.bitria.hexon.ui.board.layers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.ui.utils.geometry.HexConversions
import eric.bitria.hexon.ui.board.LocalTileSize
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HexagonalTileLayer(board: Board) {
    val tileSizePx = with(LocalDensity.current) { LocalTileSize.current.toPx() }

    // Tile Number
    val number = remember(tileSizePx) {
        Paint().asFrameworkPaint().apply {
            color = android.graphics.Color.WHITE
            textSize = tileSizePx * 0.7f
            isFakeBoldText = true
            textAlign = android.graphics.Paint.Align.CENTER
            isAntiAlias = true
            setShadowLayer(
                tileSizePx * 0.1f,
                0f, 0f,
                android.graphics.Color.BLACK
            )
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        board.getTiles().forEach { tile ->
            val center = HexConversions.axialToPixel(tile.coords.q, tile.coords.r, tileSizePx)

            drawHexagon(center, tileSizePx, tile.resource.color)

            // Draw Tile Number
            drawContext.canvas.nativeCanvas.drawText(
                tile.number.toString(),
                center.x,
                center.y - (number.ascent() + number.descent()) / 2,
                number
            )
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