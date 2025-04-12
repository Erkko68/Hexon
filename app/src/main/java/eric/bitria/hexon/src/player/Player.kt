package eric.bitria.hexon.src.player

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.exceptions.InvalidBuildingException
import eric.bitria.hexon.src.exceptions.InvalidResourceException
import eric.bitria.hexon.view.enums.GameActions

class Player(
    val color: Color
) {
    private var _victoryPoints by mutableIntStateOf(0)
    private val deck: Deck = Deck()
    private val shop: Shop = Shop(deck)

    fun getVictoryPoints(): Int = _victoryPoints
    fun setVictoryPoints(points: Int) {_victoryPoints = points}

    /**
     * Adds a resource to the player's deck.
     */
    fun addResource(resource: Resource, amount: Int){
        deck.addResource(resource,amount)
    }

    /**
     * Removes a resource from the player's deck.
     */
    fun removeBuildingResources(building: Building) {
        deck.removeBuildingResources(building)
    }

    /**
     * Checks if the player has enough resources to build the specified building.
     */
    fun hasBuildingResources(building: Building): Boolean {
        return deck.hasBuildingResources(building)
    }

    fun getDeckResources(): Map<Resource, Int> {
        return deck.getResources()
    }

    // Trading functions

    fun selectTradingResource(resource: Resource){
        shop.selectTradingResource(resource)
    }

    fun addTradingResource(resource: Resource){
        shop.addTradingResource(resource)
    }

    fun canAcceptTrade(): Boolean{
        return shop.canAcceptTrade()
    }

    fun cancelTrade(){
        shop.cancelTrade()
    }

    fun acceptTrade() {
        shop.acceptTrade()
    }
}