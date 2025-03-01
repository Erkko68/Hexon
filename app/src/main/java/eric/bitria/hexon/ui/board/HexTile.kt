package eric.bitria.hexon.ui.board

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.board.tile.Tile
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HexTile(tile: Tile, position: Offset, size: Float) {
    val hexPath = Path().apply {
        val angles = listOf(0, 60, 120, 180, 240, 300).map { it * PI / 180f }
        moveTo(position.x + size * cos(angles[0]).toFloat(), position.y + size * sin(angles[0]).toFloat())
        angles.drop(1).forEach { angle ->
            lineTo(position.x + size * cos(angle).toFloat(), position.y + size * sin(angle).toFloat())
        }
        close()
    }

    Canvas(modifier = Modifier.size((size * 2).dp)) {
        drawPath(hexPath, color = Color.Gray)
    }

    Text(
        text = tile.number.toString(),
        modifier = Modifier
            .size((size * 2).dp)
    )
}
