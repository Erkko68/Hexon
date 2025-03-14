package eric.bitria.hexon

import eric.bitria.hexon.ui.board.BoardView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.data.Building
import eric.bitria.hexon.src.data.Coord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.data.Resource
import eric.bitria.hexon.ui.theme.HexonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val board = Board(radius = 3).apply {
            // Add tiles with proper coordinates and resources
            val tile1 = Tile(Coord(0, 0), Resource.WOOD, 8)
            val tile2 = Tile(Coord(1, 0), Resource.WOOD, 8)
            addTile(tile1)
            addTile(tile2)
        }

        board.tiles.get(Coord(0, 0))!!.vertices[Direction.EAST]!!.placeBuilding(Building.SETTLEMENT)


        setContent {
            HexonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BoardView(board = board)
                }
            }
        }
    }
}