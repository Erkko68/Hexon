package eric.bitria.hexon.ui.board.layers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.board.layers.cards.ResourceCard
import eric.bitria.hexon.ui.utils.CardsContainer

@Composable
fun CardsLayer(player: Player) {

    Column(
        verticalArrangement = Arrangement.Bottom
    ){

        val resources = player.getDeckResources().keys.toTypedArray() // Get all the resources from the player's deck
        CardsContainer(
            cards = resources
        ) { resource ->
            ResourceCard(
                resource = resource,
                count = player.getDeckResources()[resource] ?: 0
            )
        }
    }
}