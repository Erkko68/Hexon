package eric.bitria.hexon.ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import eric.bitria.hexon.ui.ui.cards.BuildingCard
import eric.bitria.hexon.ui.ui.cards.ResourceCard
import eric.bitria.hexon.view.enums.GameAction
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

    // Calculate card size based on screen width
    val configuration = LocalConfiguration.current
    val localCardSize = (minOf(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp) / 8f)

    CompositionLocalProvider(LocalCardSize provides localCardSize) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.Top
            ) {}

            if(phase == GamePhase.ROLL_DICE){
                Box(modifier =
                    Modifier.weight(1f)
                ){
                    DiceScreen(
                        dice1 = dices.first,
                        dice2 = dices.second,
                        onRollClick = {
                            if (clickHandler is ClickHandler.NoParam) {
                                clickHandler.handler()
                            }
                        }
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .weight(1f),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        val buildings = Building.entries.filter { it != Building.NONE }.toTypedArray()
                        CardsContainer(
                            cards = buildings
                        ) { building ->
                            BuildingCard(
                                building = building,
                                onClick = {
                                    if (clickHandler is ClickHandler.OnBuilding) {
                                        clickHandler.handler(building)
                                    }
                                },
                                enabled = player.hasBuildingResources(building) and (phase == GamePhase.PLAYER_TURN)
                            )
                        }

                        val resources = player.getDeckResources().filter { it.value > 0 }.keys.toTypedArray()
                        CardsContainer(
                            cards = resources
                        ) { resource ->
                            ResourceCard(
                                resource = resource,
                                count = player.getDeckResources()[resource] ?: 0,
                                onClick = {
                                    if (clickHandler is ClickHandler.OnResource) {
                                        clickHandler.handler(resource)
                                    }
                                },
                            )
                        }
                        Spacer(Modifier)
                    }
                }
            }
        }
    }
}
