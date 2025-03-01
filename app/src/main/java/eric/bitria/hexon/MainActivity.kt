package eric.bitria.hexon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.data.Resource
import eric.bitria.hexon.ui.board.HexTile
import eric.bitria.hexon.ui.theme.HexonTheme
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HexonTheme {
                HexBoard()
            }
        }
    }
}


@Composable
fun HexBoard() {
    val board = remember { Board() }
    val tileSize = 100f // Adjust hex size for visibility
    val tiles = remember { mutableStateListOf<Pair<Int, Int>>() }

    // Create and place tiles in axial coordinates
    LaunchedEffect(Unit) {
        tiles.addAll(
            listOf(
                Pair(0, 0),
                Pair(1, 0),
                Pair(0, 1),
                Pair(-1, 1),
                Pair(-1, 0),
                Pair(0, -1),
                Pair(1, -1),
                Pair(-1, -1),
                Pair(2, -1),
                Pair(2, 0),
                Pair(1, 1)
            )
        )

        tiles.forEach { (q, r) ->
            board.addTile(q, r, Tile(8,Resource.WOOD))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val centerX = 400f // Approximate center offset (adjust as needed)
        val centerY = 800f // Approximate center offset (adjust as needed)

        tiles.forEach { (q, r) ->
            val tile = board.getTile(q, r) ?: return@forEach
            val position = axialToPixel(q, r, tileSize)
            HexTile(tile, Offset(position.x + centerX, position.y + centerY), tileSize)
        }
    }
}

/**
 * Converts axial coordinates (q, r) into pixel coordinates for rendering.
 */
fun axialToPixel(q: Int, r: Int, size: Float): Offset {
    val x = size * (3.0 / 2.0 * q)
    val y = size * (Math.sqrt(3.0) * (r + q / 2.0))
    return Offset(x.toFloat(), y.toFloat())
}
