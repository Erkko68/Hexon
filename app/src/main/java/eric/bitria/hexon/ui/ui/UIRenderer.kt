package eric.bitria.hexon.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.icons.None
import eric.bitria.hexon.ui.ui.cards.ActionCard
import eric.bitria.hexon.ui.ui.cards.BuildingCard
import eric.bitria.hexon.ui.ui.cards.ResourceCard
import eric.bitria.hexon.view.enums.GameActions
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.utils.CardType
import eric.bitria.hexon.view.utils.ClickHandler
import eric.bitria.hexon.view.utils.DeckType.PlayerDeck

val LocalCardSize = staticCompositionLocalOf<Dp> { error("Card size not provided") }

@Composable
fun UIRenderer(
    player: Player,
    phase: GamePhase,
    dices: Pair<Int, Int>,
    clickHandler: ClickHandler,
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
                    onRollClick = { (clickHandler as? ClickHandler.NoParam)?.handler?.invoke() },
                    modifier = Modifier.weight(1f)
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 0.dp, vertical = 24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        CardsContent(
                            player = player,
                            phase = phase,
                            clickHandler = clickHandler,
                            modifier = Modifier.weight(1f)
                        )
                        ActionContent(
                            phase = phase,
                            clickHandler = clickHandler,
                            player = player,
                            modifier = Modifier
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
    clickHandler: ClickHandler,
    modifier: Modifier,
    player: Player
) {
    val actions = GameActions.entries.filter { it.icon != Icons.None }.toTypedArray()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        ActionsContainer(
            cards = actions
        ) { action ->
            val isPhaseValid = phase == GamePhase.PLAYER_TURN || phase == GamePhase.PLAYER_TRADE
            val isAcceptTrade = action == GameActions.ACCEPT_TRADE
            val canAccept = if (isAcceptTrade) player.canAcceptTrade() else true

            ActionCard(
                action = action,
                onClick = {
                    (clickHandler as? ClickHandler.OnCard)?.handler(CardType.ActionCard(action))
                },
                enabled = isPhaseValid && canAccept
            )
        }
    }
}

@Composable
private fun CardsContent(
    player: Player,
    phase: GamePhase,
    clickHandler: ClickHandler,
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
                onClick = { (clickHandler as? ClickHandler.OnCard)?.handler(
                    CardType.BuildingCard(
                        building
                    )
                ) },
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
                onClick = { (clickHandler as? ClickHandler.OnCard)?.handler(
                    CardType.ResourceCard(
                        resource,
                        PlayerDeck(resource)
                    )
                ) }
            )
        }
    }
}