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
import eric.bitria.hexon.src.data.Building
import eric.bitria.hexon.src.data.Coord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.ui.theme.HexonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

// Create and initialize the board
        val board = Board(radius = 3).apply {
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