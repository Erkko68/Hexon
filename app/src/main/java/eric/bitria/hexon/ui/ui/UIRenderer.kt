package eric.bitria.hexon.ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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

val LocalCardSize = staticCompositionLocalOf<Dp> { error("Card size not provided") }

@Composable
fun UIRenderer(
    player: Player,
    onCardSelect: (Any) -> Unit = {}
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
            ) {
            }

            // Lower half
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
                            onClick = { onCardSelect(building) },
                            enabled = player.hasBuildingResources(building)
                        )
                    }

                    val resources = player.getDeckResources().keys.toTypedArray()
                    CardsContainer(
                        cards = resources
                    ) { resource ->
                        ResourceCard(
                            resource = resource,
                            count = player.getDeckResources()[resource] ?: 0,
                            onClick = { onCardSelect(resource) }
                        )
                    }
                    Spacer(Modifier)
                }
            }
        }
    }
}
