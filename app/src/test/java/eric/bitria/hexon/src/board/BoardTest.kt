package eric.bitria.hexon.src.board

import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.data.Resource
import eric.bitria.hexon.src.exceptions.InvalidBoardException
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertSame
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class BoardTest {

    private lateinit var board: Board

    @Before
    fun setUp() {
        // Create a new board for each test
        board = Board(3, 3)
    }

    @Test
    fun testAddTile() {
        val board = Board(3, 3)

        // Create two tiles
        val tile1 = Tile(8, Resource.WOOD)
        val tile2 = Tile(5, Resource.BRICK)

        // Add the first tile at (0,0)
        board.addTile(0, 0, tile1)
        assertTrue("Tile (0,0) should be in the board", board.hasTile(0, 0))

        // Add a second tile at (1,0), which is to the right of (0,0)
        board.addTile(1, 0, tile2)
        assertTrue("Tile (1,0) should be in the board", board.hasTile(1, 0))

        // Verify that tile1 and tile2 are linked correctly
        val tile1Edge = tile1.edges[1]  // Right edge of tile1
        val tile2Edge = tile2.edges[4]  // Left edge of tile2 (opposite edge)

        assertSame("Tile1's right edge should be the same as Tile2's left edge", tile1Edge, tile2Edge)
    }

    @Test
    fun testAddTwoTiles() {
        val board = Board(3, 3)

        // Create three tiles
        val tile1 = Tile(8, Resource.WOOD)
        val tile2 = Tile(5, Resource.BRICK)
        val tile3 = Tile(4, Resource.SHEEP)

        // Add the first tile at (0,0)
        board.addTile(0, 0, tile1)
        assertTrue("Tile (0,0) should be in the board", board.hasTile(0, 0))

        // Add a second tile at (1,0), which is to the right of (0,0)
        board.addTile(1, 0, tile2)
        assertTrue("Tile (1,0) should be in the board", board.hasTile(1, 0))

        board.addTile(-1, 0, tile3)
        assertTrue("Tile (-1,0) should be in the board", board.hasTile(-1, 0))
    }

    @Test(expected = InvalidBoardException::class)
    fun testAddTileQOutOfBounds() {
        val board = Board(3, 3)
        val tile = Tile(8, Resource.WOOD)

        // Attempt to add a tile at q = 4 (out of bounds)
        board.addTile(4, 0, tile)
    }

    @Test(expected = InvalidBoardException::class)
    fun testAddTileROutOfBounds() {
        val board = Board(3, 3)
        val tile = Tile(8, Resource.WOOD)

        // Attempt to add a tile at r = 4 (out of bounds)
        board.addTile(0, 4, tile)
    }

    @Test(expected = InvalidBoardException::class)
    fun testAddTileQAndROutOfBounds() {
        val board = Board(3, 3)
        val tile = Tile(8, Resource.WOOD)

        // Attempt to add a tile at q = 4 and r = 4 (both out of bounds)
        board.addTile(4, 4, tile)
    }

    @Test
    fun testHasTileWhenTileExists() {
        val board = Board(3, 3)
        // Add a tile at (0, 0)
        val tile = Tile(8, Resource.WOOD)
        board.addTile(0, 0, tile)

        // Verify that hasTile returns true for (0, 0)
        assertTrue("hasTile should return true for (0, 0)", board.hasTile(0, 0))
    }

    @Test
    fun testHasTileWhenTileDoesNotExist() {
        // Verify that hasTile returns false for (1, 1) (no tile added here)
        assertFalse("hasTile should return false for (1, 1)", board.hasTile(1, 1))
    }

    @Test
    fun testGetTileWhenTileExists() {
        // Add a tile at (0, 0)
        val tile = Tile(8, Resource.WOOD)
        board.addTile(0, 0, tile)

        // Verify that getTile returns the correct tile for (0, 0)
        val retrievedTile = board.getTile(0, 0)
        assertNotNull("getTile should return a non-null tile for (0, 0)", retrievedTile.toString())
        assertEquals("getTile should return the correct tile for (0, 0)", tile, retrievedTile)
    }

    @Test
    fun testGetTileWhenTileDoesNotExist() {
        // Verify that getTile returns null for (1, 1) (no tile added here)
        assertNull("getTile should return null for (1, 1)", board.getTile(1, 1))
    }

    @Test(expected = InvalidBoardException::class)
    fun testGetTileOutOfBounds() {
        // Attempt to get a tile at out-of-bounds coordinates and expect an exception
        board.getTile(4, 0)  // Should throw InvalidBoardException
        board.getTile(0, 4)  // Should throw InvalidBoardException
        board.getTile(-4, 0) // Should throw InvalidBoardException
        board.getTile(0, -4) // Should throw InvalidBoardException
    }

    @Test(expected = InvalidBoardException::class)
    fun testHasTileOutOfBounds() {
        // Attempt to check hasTile at out-of-bounds coordinates and expect an exception
        board.hasTile(4, 0)  // Should throw InvalidBoardException
        board.hasTile(0, 4)  // Should throw InvalidBoardException
        board.hasTile(-4, 0) // Should throw InvalidBoardException
        board.hasTile(0, -4) // Should throw InvalidBoardException
    }

}
