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
import eric.bitria.hexon.src.data.Resource
import eric.bitria.hexon.ui.theme.HexonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tile1 = Tile(5, Resource.BRICK)
        tile1.vertices[0].placeBuilding(Building.SETTLEMENT)

        // Initialize the board and add some tiles
        val board = Board(3,3).apply {
            addTile(0, 0, tile1)
            addTile(-1, 0, Tile(10, Resource.ORE))
            addTile(0, -1, Tile(2, Resource.DESERT))
        }


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