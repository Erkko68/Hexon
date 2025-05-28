package eric.bitria.hexon.ui.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.persistent.datastore.GameSettings
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.ui.sections.ActionsSection
import eric.bitria.hexon.ui.ui.sections.CardsSection
import eric.bitria.hexon.ui.ui.sections.DiceSection
import eric.bitria.hexon.ui.ui.sections.InfoSection
import eric.bitria.hexon.ui.ui.sections.PlayersColumn
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.utils.ClickHandler
import kotlinx.coroutines.flow.Flow

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun GameUIRenderer(
    player: Player,
    currentPlayer: Player,
    players: List<Player>,
    phase: GamePhase,
    dices: Pair<Int, Int>,
    clickHandler: ClickHandler,
    timeLeft: Long,
    settingsFlow: Flow<GameSettings>
) {
    val settings = settingsFlow.collectAsState(initial = GameSettings()).value

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val sectionWidth = maxWidth / 2
        val sectionHeight = maxHeight / 2

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .width(sectionWidth)
                        .height(sectionHeight)
                        .padding(16.dp)
                ) {
                    PlayersColumn(
                        players = players,
                        currentPlayer = currentPlayer,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Box(
                    modifier = Modifier
                        .width(sectionWidth)
                        .height(sectionHeight)
                        .padding(16.dp)
                ) {
                    InfoSection(
                        timeLeft = timeLeft,
                        player = player,
                        settings = settings,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            if (phase == GamePhase.ROLL_DICE) {
                DiceSection(
                    dices = dices,
                    enabled = (currentPlayer == player),
                    onRollClick = { (clickHandler as? ClickHandler.NoParam)?.handler?.invoke() },
                    modifier = Modifier
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .width((sectionWidth.value * 1.5).dp)
                            .height(sectionHeight)
                            .padding(16.dp)
                    ) {
                        CardsSection(
                            player = player,
                            phase = phase,
                            clickHandler = clickHandler,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width((sectionWidth.value * 0.5).dp)
                            .height(sectionHeight)
                            .padding(16.dp)
                    ) {
                        ActionsSection(
                            phase = phase,
                            clickHandler = clickHandler,
                            player = player,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}