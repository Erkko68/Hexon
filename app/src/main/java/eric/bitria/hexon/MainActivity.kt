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
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.ui.theme.HexonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tile1 = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        val tile2 = Tile(AxialCoord(0, 1), Resource.BRICK, 8)
        val tile3 = Tile(AxialCoord(-1, 1), Resource.ORE, 8)
        val tile4 = Tile(AxialCoord(2, 0), Resource.WHEAT, 8)
        val tile5 = Tile(AxialCoord(1, 0), Resource.NONE, 8)
        val tile6 = Tile(AxialCoord(+1, -1), Resource.SHEEP, 8)

        val board = Board(radius = 3).apply {
            addTile(tile1)
            addTile(tile2)
            addTile(tile3)
            addTile(tile4)
            addTile(tile5)
            addTile(tile6)
        }

        board.getTile(AxialCoord(0, 0))!!.vertices[Direction.SOUTHEAST]!!.placeBuilding(Building.SETTLEMENT)


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