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
import eric.bitria.hexon.view.enums.GameAction

class Player(
    val color: Color
) {
    private var _action by mutableStateOf(GameAction.PLACE_SETTLEMENT)
    private var _victoryPoints by mutableIntStateOf(0)
    private val deck: Deck = Deck()

    fun getAction(): GameAction = _action
    fun setAction(action: GameAction) {_action = action}

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

    internal class Deck {
        val resourceCards = mutableStateMapOf<Resource, Int>()

        fun getResources(): Map<Resource, Int> {
            return resourceCards.toMap()
        }

        /**
         * Adds the specified quantity of the resource to the deck.
         *
         * @param resource The resource to add.
         * @param quantity The quantity of the resource to add.
         */
        fun addResource(resource: Resource, quantity: Int) {
            if (resource == Resource.NONE) return
            resourceCards[resource] = (resourceCards[resource] ?: 0) + quantity
        }

        /**
         * Removes the specified quantity of the resource from the deck.
         * If there are not enough resources, the function does nothing.
         *
         * @param resource The resource to remove.
         * @param quantity The quantity of the resource to remove.
         */
        private fun removeResource(resource: Resource, quantity: Int) {
            val currentQuantity = resourceCards[resource] ?: 0
            if (currentQuantity < quantity) {
                return // Not enough resources to remove
            }
            resourceCards[resource] = currentQuantity - quantity
        }

        /**
         * Checks if all required resources for the specified building are available in sufficient quantities.
         *
         * @param building The building to check resources for.
         * @return `true` if all required resources are available, `false` otherwise.
         * @throws InvalidBuildingException If the building is `Building.NONE` (no recipe exists).
         */
        fun hasBuildingResources(building: Building): Boolean {
            if (building.recipe.isEmpty()) throw InvalidBuildingException("No recipe for $building")
            val requiredResources = building.recipe
            return requiredResources.all { (resource, requiredQuantity) ->
                val availableQuantity = resourceCards[resource] ?: 0
                availableQuantity >= requiredQuantity
            }
        }

        /**
         * Removes the required resources for the specified building from the deck.
         * This function should only be called after `hasBuildingResources` returns `true`.
         *
         * @param building The building to remove resources for.
         * @throws InvalidBuildingException If the building is `Building.NONE` (no recipe exists).
         * @throws InvalidResourceException If there are not enough resources for the building.
         */
        fun removeBuildingResources(building: Building) {
            if (building == Building.NONE) throw InvalidBuildingException("No recipe for NONE")
            if (!hasBuildingResources(building)) {
                throw InvalidResourceException("Not enough resources for building. Building: $building. Call hasBuildingResources first.")
            }

            // Remove the required resources
            building.recipe.forEach { (resource, quantity) ->
                removeResource(resource, quantity)
            }
        }
    }
}