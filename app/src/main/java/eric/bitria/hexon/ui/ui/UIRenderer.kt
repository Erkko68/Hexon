package eric.bitria.hexon.ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.ui.cards.ActionCard
import eric.bitria.hexon.ui.ui.cards.BuildingCard
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
    cardClickHandler: ClickHandler,
    actionClickHandler: ClickHandler
) {
    val configuration = LocalConfiguration.current
    val localCardSize = minOf(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp) / 8f

    CompositionLocalProvider(LocalCardSize provides localCardSize) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            // Temporal
            TopSpacerSection(Modifier.weight(1f))

            if (phase == GamePhase.ROLL_DICE) {
                DiceSection(
                    dices = dices,
                    onRollClick = { (actionClickHandler as? ClickHandler.NoParam)?.handler() },
                    modifier = Modifier.weight(1f)
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CardsContent(
                            player = player,
                            phase = phase,
                            cardClickHandler = cardClickHandler,
                            modifier = Modifier.weight(1f)
                        )
                        ActionContent(
                            phase = phase,
                            actionClickHandler = actionClickHandler,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TopSpacerSection(modifier: Modifier = Modifier) {
    Row(modifier = modifier) { /* Empty spacer */ }
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
private fun ActionContent(
    phase: GamePhase,
    actionClickHandler: ClickHandler,
    modifier: Modifier
) {
    val actions = GameActions.entries.filter { it.type == GameActions.Type.ACTION }.toTypedArray()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CardsContainer(
            cards = actions
        ) { action ->
            ActionCard(
                action = action,
                onClick = { (actionClickHandler as? ClickHandler.OnAction)?.handler(action) },
                enabled = (phase == GamePhase.PLAYER_TURN)
            )
        }
    }
}

@Composable
private fun CardsContent(
    player: Player,
    phase: GamePhase,
    cardClickHandler: ClickHandler,
    modifier: Modifier
) {
    val resources = player.getDeckResources().filter { it.value > 0 }.keys.toTypedArray()
    val buildings = Building.entries.filter { it != Building.NONE }.toTypedArray()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CardsContainer(
            cards = buildings
        ) { building ->
            BuildingCard(
                building = building,
                onClick = { (cardClickHandler as? ClickHandler.OnBuilding)?.handler(building) },
                enabled = player.hasBuildingResources(building) && (phase == GamePhase.PLAYER_TURN)
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        CardsContainer(
            cards = resources
        ) { resource ->
            ResourceCard(
                enabled = true,
                resource = resource,
                count = player.getDeckResources()[resource] ?: 0,
                onClick = { (cardClickHandler as? ClickHandler.OnResource)?.handler(resource) }
            )
        }
    }
}