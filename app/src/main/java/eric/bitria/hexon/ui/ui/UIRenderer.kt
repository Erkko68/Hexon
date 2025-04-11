package eric.bitria.hexon.ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.ui.cards.ActionCard
import eric.bitria.hexon.ui.ui.cards.ResourceCard
import eric.bitria.hexon.view.enums.GameActions
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.utils.ClickHandler

val LocalCardSize = staticCompositionLocalOf<Dp> { error("Card size not provided") }

@Composable
fun UIRenderer(
    player: Player,
    phase: GamePhase,
    dices: Pair<Int, Int>,
    clickHandler: ClickHandler
) {
    val configuration = LocalConfiguration.current
    val localCardSize = minOf(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp) / 8f

    CompositionLocalProvider(LocalCardSize provides localCardSize) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopSpacerSection(Modifier.weight(1f))

            if (phase == GamePhase.ROLL_DICE) {
                DiceSection(
                    dices = dices,
                    onRollClick = { (clickHandler as? ClickHandler.NoParam)?.handler() },
                    modifier = Modifier.weight(1f)
                )
            } else {
                MainGameContent(
                    player = player,
                    phase = phase,
                    clickHandler = clickHandler,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun TopSpacerSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) { /* Empty spacer */ }
}

@Composable
private fun DiceSection(
    dices: Pair<Int, Int>,
    onRollClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        DiceScreen(
            dice1 = dices.first,
            dice2 = dices.second,
            onRollClick = onRollClick
        )
    }
}

@Composable
private fun MainGameContent(
    player: Player,
    phase: GamePhase,
    clickHandler: ClickHandler,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GameActionsSection(
                player = player,
                phase = phase,
                clickHandler = clickHandler
            )
            ResourcesSection(
                player = player,
                clickHandler = clickHandler
            )
            Spacer(modifier = Modifier) // Bottom spacing
        }
    }
}

@Composable
private fun GameActionsSection(
    player: Player,
    phase: GamePhase,
    clickHandler: ClickHandler,
    modifier: Modifier = Modifier
) {
    val buildings = GameActions.entries.filter { it.type == GameActions.Type.BUILD }.toTypedArray()
    val actions = GameActions.entries.filter { it.type == GameActions.Type.ACTION }.toTypedArray()

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        CardsContainer(
            modifier = Modifier.weight(1f),
            cards = buildings
        ) { building ->
            ActionCard(
                action = building,
                onClick = { (clickHandler as? ClickHandler.OnAction)?.handler(building) },
                enabled = player.hasBuildingResources(building.card) && (phase == GamePhase.PLAYER_TURN)
            )
        }

        CardsContainer(
            modifier = Modifier.weight(1f),
            cards = actions
        ) { action ->
            ActionCard(
                action = action,
                onClick = { (clickHandler as? ClickHandler.OnAction)?.handler(action) },
                enabled = (phase == GamePhase.PLAYER_TURN)
            )
        }
    }
}

@Composable
private fun ResourcesSection(
    player: Player,
    clickHandler: ClickHandler,
    modifier: Modifier = Modifier
) {
    val resources = player.getDeckResources().filter { it.value > 0 }.keys.toTypedArray()

    CardsContainer(
        modifier = modifier,
        cards = resources
    ) { resource ->
        ResourceCard(
            resource = resource,
            count = player.getDeckResources()[resource] ?: 0,
            onClick = { (clickHandler as? ClickHandler.OnResource)?.handler(resource) }
        )
    }
}