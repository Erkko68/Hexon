package eric.bitria.hexon.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import eric.bitria.hexon.R
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.board.BoardRenderer
import eric.bitria.hexon.ui.board.ZoomContainer
import eric.bitria.hexon.ui.ui.GameUIRenderer
import eric.bitria.hexon.view.MainGameViewModel
import eric.bitria.hexon.view.enums.GamePhase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun GameScreen(viewModel: MainGameViewModel, onExitToMenu: () -> Unit) {
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
        timeLeft = viewModel.timeLeft
    )

    if (viewModel.gamePhase == GamePhase.PLAYER_WON) {
        EndScreen(
            onExitToMenu = onExitToMenu,
            onShareResults = { email ->
                val emailBody = generateGameResultEmail(context, viewModel.humanPlayer, viewModel.currentPlayer)
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:$email".toUri()
                    putExtra(Intent.EXTRA_SUBJECT, "Game Results")
                    putExtra(Intent.EXTRA_TEXT, emailBody)
                }

                context.startActivity(Intent.createChooser(intent,
                    context.getString(R.string.send_game_results)))
            },
            player = viewModel.humanPlayer,
            winner = viewModel.currentPlayer
        )
    }
}

fun generateGameResultEmail(context: Context, player: Player, winner: Player): String {
    val sb = StringBuilder()
    val dateFormat = SimpleDateFormat("MMMM d, yyyy 'at' h:mm a", Locale.getDefault())
    val currentTime = dateFormat.format(Date())

    sb.appendLine(context.getString(R.string.game_results))
    sb.appendLine(context.getString(R.string.played_on, currentTime))
    sb.appendLine()
    sb.appendLine(context.getString(R.string.winner, winner.name))
    sb.appendLine()
    sb.appendLine(context.getString(R.string.your_stats))
    sb.appendLine()

    sb.appendLine(context.getString(R.string.buildings))
    player.getTotalBuildings()
        .filterKeys { it != Building.NONE }
        .forEach { (building, count) ->
            sb.appendLine("- ${building.name}: $count")
        }

    sb.appendLine()

    sb.appendLine(context.getString(R.string.resources))
    player.getTotalResources()
        .filterKeys { it != Resource.NONE }
        .forEach { (resource, count) ->
            sb.appendLine("- ${resource.name}: $count")
        }

    sb.appendLine()
    sb.appendLine(context.getString(R.string.thanks_for_playing))

    return sb.toString()
}