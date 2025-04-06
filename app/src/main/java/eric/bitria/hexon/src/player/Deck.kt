package eric.bitria.hexon.src.player

import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.exceptions.InvalidBuildingException
import eric.bitria.hexon.src.exceptions.InvalidResourceException

class Deck {

    val resourceCards = mutableMapOf<Resource, Int>()

    /**
     * Adds the specified quantity of the resource to the deck.
     *
     * @param resource The resource to add.
     * @param quantity The quantity of the resource to add.
     */
    fun addResource(resource: Resource, quantity: Int) {
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