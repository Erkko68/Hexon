package eric.bitria.hexon.src.player

import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.exceptions.InvalidTradeException
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ShopTest {
    private lateinit var playerDeck: Deck
    private lateinit var shop: Shop

    @Before
    fun setup() {
        playerDeck = Deck()
        shop = Shop(playerDeck)
    }

    @Test
    fun testCanAcceptTrade_Valid() {
        // Setup: Player offers 4 resources for 1 shop resource
        repeat(4) { shop.addTradingResource(Resource.WOOD) }
        shop.selectTradingResource(Resource.BRICK)

        assertTrue(shop.canAcceptTrade())
    }

    @Test
    fun testCanAcceptTrade_InvalidNotEnoughResources() {
        // Setup: Player offers 3 resources for 1 shop resource
        repeat(3) { shop.addTradingResource(Resource.WOOD) }
        shop.selectTradingResource(Resource.BRICK)

        assertFalse(shop.canAcceptTrade())
    }

    @Test
    fun testCanAcceptTrade_InvalidNoShopResources() {
        repeat(4) { shop.addTradingResource(Resource.WOOD) }
        // No shop resources selected
        assertFalse(shop.canAcceptTrade())
    }

    @Test
    fun testAcceptTrade_ExactAmount() {
        // Setup: Player has 4 WOOD in main deck
        playerDeck.addResource(Resource.WOOD, 4)
        repeat(4) { shop.addTradingResource(Resource.WOOD) }
        shop.selectTradingResource(Resource.BRICK)

        shop.acceptTrade()

        // Verify: 4 WOOD removed, 1 BRICK added
        assertEquals(0, playerDeck.getResources()[Resource.WOOD])
        assertEquals(1, playerDeck.getResources()[Resource.BRICK])
    }

    @Test
    fun testAcceptTrade_MultipleResources() {
        // Setup: 2 shop resources (needs 8 player resources)
        playerDeck.addResource(Resource.WOOD, 5)
        playerDeck.addResource(Resource.BRICK, 5)
        repeat(5) { shop.addTradingResource(Resource.WOOD) }
        repeat(3) { shop.addTradingResource(Resource.BRICK) }
        repeat(2) { shop.selectTradingResource(Resource.SHEEP) }

        shop.acceptTrade()

        // Verify: 8 total removed (5 WOOD + 3 BRICK)
        assertEquals(0, playerDeck.getResources()[Resource.WOOD])
        assertEquals(2, playerDeck.getResources()[Resource.BRICK])
        assertEquals(2, playerDeck.getResources()[Resource.SHEEP])
    }

    @Test
    fun testAcceptTrade_PreservesExtraResources() {
        // Setup: Player offers 5 resources but only needs 4
        playerDeck.addResource(Resource.WOOD, 5)
        repeat(5) { shop.addTradingResource(Resource.WOOD) }
        shop.selectTradingResource(Resource.BRICK)

        shop.acceptTrade()

        // Verify: 4 removed, 1 remains
        assertEquals(1, playerDeck.getResources()[Resource.WOOD])
        assertEquals(1, playerDeck.getResources()[Resource.BRICK])
    }

    @Test(expected = InvalidTradeException::class)
    fun testAcceptTrade_InvalidTradeThrows() {
        // Setup: Invalid trade (only 3 resources offered)
        repeat(3) { shop.addTradingResource(Resource.WOOD) }
        shop.selectTradingResource(Resource.BRICK)

        shop.acceptTrade()
    }

    @Test
    fun testCancelTrade_ClearsDecks() {
        shop.addTradingResource(Resource.WOOD)
        shop.selectTradingResource(Resource.BRICK)

        shop.cancelTrade()

        assertTrue(shop.getPlayerTemporalDeck().isEmpty())
        assertTrue(shop.getShopTemporalDeck().isEmpty())
    }

    // Helper extension functions for test visibility
    private fun Shop.getPlayerTemporalDeck() = this::class.java
        .getDeclaredField("playerTemporalDeck").apply { isAccessible = true }
        .get(this) as Deck

    private fun Shop.getShopTemporalDeck() = this::class.java
        .getDeclaredField("shopTemporalDeck").apply { isAccessible = true }
        .get(this) as Deck

    private fun Deck.isEmpty() = this.getResources().values.sum() == 0
}