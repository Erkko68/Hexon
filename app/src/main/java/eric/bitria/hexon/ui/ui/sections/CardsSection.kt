package eric.bitria.hexon.ui.ui.sections

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.ui.cards.BuildingCard
import eric.bitria.hexon.ui.ui.cards.ResourceCard
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.utils.CardType
import eric.bitria.hexon.view.utils.ClickHandler
import eric.bitria.hexon.view.utils.DeckType.PlayerDeck
import eric.bitria.hexon.view.utils.DeckType.SystemDeck
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CardsSection(
    player: Player,
    phase: GamePhase,
    clickHandler: ClickHandler,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.BottomStart
    ) {
        val spacing = 8.dp

        val widthBased = (maxWidth * 0.15f).coerceIn(40.dp, 72.dp)
        val heightBased = ((maxHeight - spacing) / 2f) / 1.618f
        val cardWidth = min(widthBased, heightBased)
        val cardHeight = cardWidth * 1.618f

        val resources = player.getDeckResources().filter { it.value > 0 }.keys.sorted().toTypedArray()
        val allResources = Resource.entries.filter { it != Resource.NONE }.sorted().toTypedArray()
        val buildings = Building.entries.filter { it != Building.NONE }.sorted().toTypedArray()

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing)
        ) {
            if (phase == GamePhase.PLAYER_TRADE) {
                CardsRow(
                    cards = allResources,
                    cardWidth = cardWidth,
                    rowHeight = cardHeight
                ) { resource ->
                    ResourceCard(
                        resource = resource,
                        count = player.getSystemTradeDeckResources()[resource] ?: 0,
                        onClick = {
                            (clickHandler as? ClickHandler.OnCard)?.handler(
                                CardType.ResourceCard(resource, SystemDeck(resource))
                            )
                        }
                    )
                }
            } else {
                CardsRow(
                    cards = buildings,
                    cardWidth = cardWidth,
                    rowHeight = cardHeight
                ) { building ->
                    BuildingCard(
                        building = building,
                        onClick = {
                            (clickHandler as? ClickHandler.OnCard)?.handler(
                                CardType.BuildingCard(building)
                            )
                        },
                        enabled = player.hasBuildingResources(building) && phase == GamePhase.PLAYER_TURN
                    )
                }
            }

            CardsRow(
                cards = resources,
                cardWidth = cardWidth,
                rowHeight = cardHeight
            ) { resource ->
                ResourceCard(
                    enabled = true,
                    selected = player.getPlayerTradeDeckResources()[resource] ?: 0,
                    resource = resource,
                    count = player.getDeckResources()[resource] ?: 0,
                    onClick = {
                        (clickHandler as? ClickHandler.OnCard)?.handler(
                            CardType.ResourceCard(resource, PlayerDeck(resource))
                        )
                    }
                )
            }
        }
    }
}


@Composable
private fun <T : Enum<T>> CardsRow(
    cards: Array<T>,
    cardWidth: Dp,
    rowHeight: Dp,
    content: @Composable (T) -> Unit
) {
    Box(
        modifier = Modifier
            .height(rowHeight),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            cards.forEach { card ->
                Box(modifier = Modifier.width(cardWidth)) {
                    content(card)
                }
            }
        }
    }
}
