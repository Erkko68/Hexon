package eric.bitria.hexon.ui.board.layers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.ui.board.LocalTileSize
import eric.bitria.hexon.ui.utils.geometry.HexConversions
import eric.bitria.hexon.ui.utils.geometry.drawHexagon

@Composable
fun HexagonalTileLayer(tiles: List<Tile>) {
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
        tiles.forEach { tile ->
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
