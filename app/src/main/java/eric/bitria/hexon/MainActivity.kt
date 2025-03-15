package eric.bitria.hexon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.board.BoardContainer
import eric.bitria.hexon.ui.board.BoardRenderer
import eric.bitria.hexon.ui.theme.HexonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val player = Player()

        // Initialize board with tiles
        val board = Board(radius = 3).apply {
            addTile(Tile(AxialCoord(0, 0), Resource.WOOD, 8))
            addTile(Tile(AxialCoord(0, 1), Resource.BRICK, 8))
            addTile(Tile(AxialCoord(-1, 1), Resource.ORE, 8))
            addTile(Tile(AxialCoord(2, 0), Resource.WHEAT, 8))
            addTile(Tile(AxialCoord(1, 0), Resource.NONE, 8))
            addTile(Tile(AxialCoord(1, -1), Resource.SHEEP, 8))
        }

        // Place initial settlement
        board.getTile(AxialCoord(0, 0))?.let { tile ->
            tile.vertices[Direction.SOUTHEAST]?.placeBuilding(Building.SETTLEMENT, player)
        }

        setContent {
            HexonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen(board = board)
                }
            }
        }
    }
}

@Composable
fun GameScreen(board: Board) {
    BoardContainer(board = board) { zoomState ->
        BoardRenderer(
            board = board,
            zoomState = zoomState,
            tileSize = 32.dp
        )
    }
}