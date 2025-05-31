package eric.bitria.hexon.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import eric.bitria.hexon.R
import eric.bitria.hexon.persistent.database.toEmailString
import eric.bitria.hexon.ui.board.BoardRenderer
import eric.bitria.hexon.ui.board.ZoomContainer
import eric.bitria.hexon.ui.ui.GameUIRenderer
import eric.bitria.hexon.view.MainGameViewModel
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.models.LogManager

@Composable
fun GameScreen(
    viewModel: MainGameViewModel,
    onExitToMenu: () -> Unit
) {
    val context = LocalContext.current
    val gameSettings by viewModel.settingsManager.settings.collectAsState()
    val log = LogManager.logsToString(viewModel.gameLogs)

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
        players = viewModel.playerManager.allPlayers,
        phase = viewModel.gamePhase,
        clickHandler = viewModel.cardClickHandler,
        dices = viewModel.dices,
        timeLeft = viewModel.timeLeft,
        settingsFlow = viewModel.gameSettingsFlow,
        gameLogs = viewModel.gameLogs
    )

    if (viewModel.gamePhase == GamePhase.PLAYER_WON) {
        val gameResult = viewModel.generateGameResult()

        EndScreen(
            onExitToMenu = onExitToMenu,
            onShareResults = {
                val emailText = gameResult.toEmailString(context) + log
                val uri =
                    "mailto:${gameSettings.playerEmail}?subject=${Uri.encode("Game Results")}&body=${Uri.encode(emailText)}".toUri()
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = uri
                }

                context.startActivity(
                    Intent.createChooser(intent, context.getString(R.string.send_game_results))
                )
            },
            gameResult = gameResult
        )
    }

}