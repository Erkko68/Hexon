package eric.bitria.hexon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import eric.bitria.hexon.ui.board.BoardRenderer
import eric.bitria.hexon.ui.board.ZoomContainer
import eric.bitria.hexon.ui.theme.HexonTheme
import eric.bitria.hexon.ui.ui.UIRenderer
import eric.bitria.hexon.view.GameViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HexonTheme {
                ZoomContainer(viewModel.board) {
                    BoardRenderer(
                        board = viewModel.board,
                        vertices = viewModel.availableVertices,
                        edges = viewModel.availableEdges,
                        clickHandler = viewModel.boardClickHandler
                    )
                }
                UIRenderer(
                    player = viewModel.player,
                    phase = viewModel.phase,
                    cardClickHandler = viewModel.cardClickHandler,
                    actionClickHandler = viewModel.actionClickHandler,
                    dices = viewModel.dices
                )
            }
        }
    }
}