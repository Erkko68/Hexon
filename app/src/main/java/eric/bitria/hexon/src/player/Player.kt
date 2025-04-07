package eric.bitria.hexon.src.player

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import eric.bitria.hexon.src.data.game.Resource

class Player(
    val color: Color
) {
    var victoryPoints by mutableIntStateOf(0)

    val deck: Deck = Deck()

    /**
     * Adds a resource to the player's deck.
     */
    fun addResource(resource: Resource, amount: Int){
        deck.addResource(resource,amount)
    }
}