package eric.bitria.hexon.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import eric.bitria.hexon.R
import eric.bitria.hexon.persistent.database.toEmailString
import eric.bitria.hexon.ui.board.BoardRenderer
import eric.bitria.hexon.ui.board.ZoomContainer
import eric.bitria.hexon.ui.ui.GameUIRenderer
import eric.bitria.hexon.view.MainGameViewModel
import eric.bitria.hexon.view.enums.GamePhase

@Composable
fun GameScreen(
    viewModel: MainGameViewModel,
    onExitToMenu: () -> Unit
) {
    val context = LocalContext.current

    ZoomContainer(viewModel.board) {
        BoardRenderer(
            board = viewModel.board,
            vertices = viewModel.availableVertices,
            edges = viewModel.availableEdges,
            clickHandler = viewModel.boardClickHandler
        )
    }

    GameUIRenderer(
        player = viewModel.humanPlayer,
        currentPlayer = viewModel.currentPlayer,
        phase = viewModel.gamePhase,
        clickHandler = viewModel.cardClickHandler,
        dices = viewModel.dices,
        timeLeft = viewModel.timeLeft,
        settingsFlow = viewModel.gameSettingsFlow
    )

    if (viewModel.gamePhase == GamePhase.PLAYER_WON) {
        EndScreen(
            onExitToMenu = onExitToMenu,
            onShareResults = { email ->
                val gameResult = viewModel.generateGameResult()
                val emailText = gameResult.toEmailString(context)

                val uri =
                    "mailto:$email?subject=${Uri.encode("Game Results")}&body=${Uri.encode(emailText)}".toUri()
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = uri
                }

                context.startActivity(Intent.createChooser(intent,
                    context.getString(R.string.send_game_results)))
            },
            player = viewModel.humanPlayer,
            winner = viewModel.currentPlayer
        )
    }
}