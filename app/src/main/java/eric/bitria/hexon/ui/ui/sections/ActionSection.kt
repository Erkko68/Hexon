package eric.bitria.hexon.ui.ui.sections

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.ui.cards.ActionCard
import eric.bitria.hexon.view.enums.GameActions
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.utils.CardType
import eric.bitria.hexon.view.utils.ClickHandler

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ActionsSection(
    phase: GamePhase,
    clickHandler: ClickHandler,
    player: Player,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        val maxW = maxWidth
        val maxH = maxHeight

        val spacing = 8.dp
        val totalActions = 3 // END_TURN, OPEN/CLOSE_TRADE, ACCEPT_TRADE

        // Calculate the max height available per card (spacing included)
        val availableHeightPerCard = (maxH - spacing * (totalActions - 1)) / totalActions
        val scaledCardSize = minOf((maxW * 0.4f), availableHeightPerCard).coerceIn(48.dp, 80.dp)
        val cardColumnWidth = scaledCardSize * 1.618f

        val availableActions = when (phase) {
            GamePhase.PLAYER_TRADE -> listOf(
                GameActions.END_TURN,
                GameActions.CLOSE_TRADE,
                GameActions.ACCEPT_TRADE
            ).sorted()
            else -> listOf(
                GameActions.END_TURN,
                GameActions.OPEN_TRADE,
                GameActions.ACCEPT_TRADE // Rendered but invisible
            ).sorted()
        }

        Column(
            modifier = Modifier
                .width(cardColumnWidth)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(spacing),
            horizontalAlignment = Alignment.End
        ) {
            availableActions.forEach { action ->
                val isActionAllowedPhase = phase in listOf(GamePhase.PLAYER_TURN, GamePhase.PLAYER_TRADE)
                val canPerformAction = if (action == GameActions.ACCEPT_TRADE) {
                    player.canAcceptTrade()
                } else true

                val alpha = if (action == GameActions.ACCEPT_TRADE && phase != GamePhase.PLAYER_TRADE) 0f else 1f

                Box(
                    modifier = Modifier
                        .width(scaledCardSize)
                        .alpha(alpha)
                ) {
                    ActionCard(
                        action = action,
                        onClick = {
                            (clickHandler as? ClickHandler.OnCard)
                                ?.handler(CardType.ActionCard(action))
                        },
                        enabled = isActionAllowedPhase && canPerformAction
                    )
                }
            }
        }
    }
}