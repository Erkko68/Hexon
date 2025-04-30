package eric.bitria.hexon.ui.screen

import androidx.compose.runtime.Composable
import eric.bitria.hexon.ui.board.BoardRenderer
import eric.bitria.hexon.ui.board.ZoomContainer
import eric.bitria.hexon.ui.ui.GameUIRenderer
import eric.bitria.hexon.view.GameViewModel
import eric.bitria.hexon.view.enums.GamePhase

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
        currentPlayer = viewModel.currentPlayer,
        phase = viewModel.phase,
        clickHandler = viewModel.cardClickHandler,
        dices = viewModel.dices,
        timeLeft = viewModel.timeLeft
    )

    if (viewModel.phase == GamePhase.PLAYER_WON){
        EndScreen(
            onExitToMenu = onExitToMenu,
            player = viewModel.player,
            winner = viewModel.currentPlayer
        )
    }
}
