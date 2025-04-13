package eric.bitria.hexon.src.player

import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.exceptions.InvalidTradeException

class Shop(
    val playerDeck: Deck
) {

    // Deck that holds the selected resources the player will trade
    private val playerTemporalDeck = Deck()
    private val shopTemporalDeck = Deck()

    fun getSystemTradeDeckResources(): Map<Resource, Int> {
        return shopTemporalDeck.getResources()
    }

    fun getPlayerTradeDeckResources(): Map<Resource, Int> {
        return playerTemporalDeck.getResources()
    }

    fun selectTradingResource(resource: Resource){
        shopTemporalDeck.addResource(resource,1)
    }

    fun addTradingResource(resource: Resource) {
        val available = playerDeck.getResources()[resource] ?: 0
        val alreadySelected = playerTemporalDeck.getResources()[resource] ?: 0

        if (alreadySelected < available) {
            playerTemporalDeck.addResource(resource, 1)
        }
    }


    fun canAcceptTrade(): Boolean{
        // Each traded card costs 4 cards from the player's deck
        val value = shopTemporalDeck.getResources().values.sum() * 4
        return (value > 0) and
                (playerTemporalDeck.getResources().values.sum() >= value)

    }

    fun cancelTrade(){
        playerTemporalDeck.getResources().forEach { (resource, quantity) ->
            playerTemporalDeck.removeResource(resource,quantity)
        }
        shopTemporalDeck.getResources().forEach { (resource, quantity) ->
            shopTemporalDeck.removeResource(resource,quantity)
        }
    }

    fun acceptTrade() {
        if (!canAcceptTrade()) throw InvalidTradeException("Invalid trade, call canAcceptTrade first!")

        // 1. Calculate total cost (4 resources per shop resource)
        val shopResourcesCount = shopTemporalDeck.getResources().values.sum()
        val totalCost = shopResourcesCount * 4

        // 2. Remove exactly 'totalCost' resources from player's main deck
        //    (using the resources they selected in playerTemporalDeck)
        var removedCount = 0
        playerTemporalDeck.getResources().forEach { (resource, quantity) ->
            val canRemove = minOf(quantity, totalCost - removedCount)
            if (canRemove > 0) {
                playerDeck.removeResource(resource, canRemove)
                removedCount += canRemove
            }
        }

        // 3. Add all shop resources to player's deck
        shopTemporalDeck.getResources().forEach { (resource, quantity) ->
            playerDeck.addResource(resource, quantity)
        }

        // 4. Clear the trade
        cancelTrade()
    }

}