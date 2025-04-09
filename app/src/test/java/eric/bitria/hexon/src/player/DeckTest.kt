package eric.bitria.hexon.src.player

import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.exceptions.InvalidBuildingException
import eric.bitria.hexon.src.exceptions.InvalidResourceException
import eric.bitria.hexon.src.player.Player.Deck
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeckTest {

    private lateinit var deck: Deck

    @Before
    fun setup() {
        deck = Deck()
    }

    @Test
    fun testRemoveBuildingResources_Success() {
        deck.addResource(Resource.WOOD, 1)
        deck.addResource(Resource.BRICK, 1)
        deck.addResource(Resource.WHEAT, 1)
        deck.addResource(Resource.SHEEP, 1)

        assertTrue(deck.hasBuildingResources(Building.SETTLEMENT)) // Verify resources are available
        deck.removeBuildingResources(Building.SETTLEMENT) // Successfully removes resources
        assertEquals(0, deck.resourceCards[Resource.WOOD]) // Verify resources are removed
        assertEquals(0, deck.resourceCards[Resource.BRICK])
        assertEquals(0, deck.resourceCards[Resource.WHEAT])
        assertEquals(0, deck.resourceCards[Resource.SHEEP])
    }

    @Test(expected = InvalidResourceException::class)
    fun testRemoveBuildingResources_Failure() {
        deck.addResource(Resource.WOOD, 1)
        deck.addResource(Resource.BRICK, 1)
        deck.addResource(Resource.WHEAT, 1)
        // Missing SHEEP

        assertFalse(deck.hasBuildingResources(Building.SETTLEMENT)) // Verify resources are insufficient
        deck.removeBuildingResources(Building.SETTLEMENT) // Throws InvalidResourceException
    }

    @Test(expected = InvalidBuildingException::class)
    fun testRemoveBuildingResources_None() {
        deck.removeBuildingResources(Building.NONE) // Throws InvalidBuildingException for NONE
    }

    @Test
    fun testRemoveBuildingResources_City() {
        deck.addResource(Resource.WHEAT, 2)
        deck.addResource(Resource.ORE, 3)

        assertTrue(deck.hasBuildingResources(Building.CITY)) // Verify resources are available
        deck.removeBuildingResources(Building.CITY) // Successfully removes resources
        assertEquals(0, deck.resourceCards[Resource.WHEAT]) // Verify resources are removed
        assertEquals(0, deck.resourceCards[Resource.ORE])
    }

    @Test(expected = InvalidResourceException::class)
    fun testRemoveBuildingResources_WithoutCheck() {
        deck.addResource(Resource.WHEAT, 1) // Not enough for CITY
        deck.removeBuildingResources(Building.CITY) // Throws InvalidResourceException
    }
}