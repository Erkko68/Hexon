package eric.bitria.hexon.screen

import androidx.compose.runtime.Composable
import eric.bitria.hexon.ui.board.BoardRenderer
import eric.bitria.hexon.ui.board.ZoomContainer
import eric.bitria.hexon.ui.ui.GameUIRenderer
import eric.bitria.hexon.view.GameViewModel

@Composable
fun GameScreen(viewModel: GameViewModel, onExitToMenu: () -> Unit) {
    ZoomContainer(viewModel.board) {
        BoardRenderer(
            board = viewModel.board,
            vertices = viewModel.availableVertices,
            edges = viewModel.availableEdges,
            clickHandler = viewModel.boardClickHandler
        )
    }

    GameUIRenderer(
        player = viewModel.player,
        phase = viewModel.phase,
        clickHandler = viewModel.cardClickHandler,
        dices = viewModel.dices,
        timeLeft = viewModel.timeLeft
    )
}
