package eric.bitria.hexon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.board.BoardRenderer
import eric.bitria.hexon.ui.board.ZoomContainer
import eric.bitria.hexon.ui.board.layers.CardsLayer
import eric.bitria.hexon.ui.theme.HexonTheme
import eric.bitria.hexon.view.GameViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HexonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen(
                        board = viewModel.board,
                        player = viewModel.player,
                    )
                }
            }
        }
    }
}

@Composable
fun GameScreen(
    board: Board,
    player: Player,
) {
    ZoomContainer(board) {
        BoardRenderer(
            board = board,
            player = player
        )
    }
    CardsLayer(player = player)
}