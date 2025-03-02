package eric.bitria.hexon.src.board

import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.data.Resource
import eric.bitria.hexon.src.exceptions.InvalidBoardException
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {

    private lateinit var board: Board

    @BeforeEach
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
        assertTrue(board.hasTile(0, 0), "Tile (0,0) should be in the board")

        // Add a second tile at (1,0), which is to the right of (0,0)
        board.addTile(1, 0, tile2)
        assertTrue(board.hasTile(1, 0), "Tile (1,0) should be in the board")

        // Verify that tile1 and tile2 are linked correctly
        val tile1Edge = tile1.edges[1]  // Right edge of tile1
        val tile2Edge = tile2.edges[4]  // Left edge of tile2 (opposite edge)

        assertSame(tile1Edge, tile2Edge, "Tile1's right edge should be the same as Tile2's left edge")
    }

    @Test
    fun testAddTwoTiles() {
        val board = Board(3, 3)

        // Create two tiles
        val tile1 = Tile(8, Resource.WOOD)
        val tile2 = Tile(5, Resource.BRICK)
        val tile3 = Tile(4, Resource.SHEEP)

        // Add the first tile at (0,0)
        board.addTile(0, 0, tile1)
        assertTrue(board.hasTile(0, 0), "Tile (0,0) should be in the board")

        // Add a second tile at (1,0), which is to the right of (0,0)
        board.addTile(1, 0, tile2)
        assertTrue(board.hasTile(1, 0), "Tile (1,0) should be in the board")

        board.addTile(-1, 0, tile3)
        assertTrue(board.hasTile(-1, 0), "Tile (-1,0) should be in the board")

        // Verify that tile1 and tile2 are linked correctly
        val tile1RightEdge = tile1.edges[1]  // Right edge of tile1
        val tile1LeftEdge = tile1.edges[4]  // Left edge of tile1

        val tile2Edge = tile2.edges[4]  // Left edge of tile2 (opposite edge)

        val tile3Edge = tile3.edges[1]  // Right edge of tile3 (opposite edge)

        assertSame(tile1RightEdge, tile2Edge, "Tile1's right edge should be the same as Tile2's left edge")
        assertSame(tile1LeftEdge, tile3Edge, "Tile1's left edge should be the same as Tile3's right edge")
    }

    @Test
    fun testAddTileQOutOfBounds() {
        val board = Board(3, 3)
        val tile = Tile(8, Resource.WOOD)

        // Attempt to add a tile at q = 4 (out of bounds)
        assertThrows<InvalidBoardException> {
            board.addTile(4, 0, tile)
        }
    }

    @Test
    fun testAddTileROutOfBounds() {
        val board = Board(3, 3)
        val tile = Tile(8, Resource.WOOD)

        // Attempt to add a tile at r = 4 (out of bounds)
        assertThrows<InvalidBoardException> {
            board.addTile(0, 4, tile)
        }
    }

    @Test
    fun testAddTileQAndROutOfBounds() {
        val board = Board(3, 3)
        val tile = Tile(8, Resource.WOOD)

        // Attempt to add a tile at q = 4 and r = 4 (both out of bounds)
        assertThrows<InvalidBoardException> {
            board.addTile(4, 4, tile)
        }
    }

    @Test
    fun testHasTileWhenTileExists() {
        val board = Board(3, 3)
        // Add a tile at (0, 0)
        val tile = Tile(8, Resource.WOOD)
        board.addTile(0, 0, tile)

        // Verify that hasTile returns true for (0, 0)
        assertTrue(board.hasTile(0, 0), "hasTile should return true for (0, 0)")
    }

    @Test
    fun testHasTileWhenTileDoesNotExist() {
        // Verify that hasTile returns false for (1, 1) (no tile added here)
        assertFalse(board.hasTile(1, 1), "hasTile should return false for (1, 1)")
    }

    @Test
    fun testHasTileOutOfBounds() {
        // Verify that hasTile returns false for out-of-bounds coordinates
        assertFalse(board.hasTile(4, 0), "hasTile should return false for out-of-bounds coordinates (4, 0)")
        assertFalse(board.hasTile(0, 4), "hasTile should return false for out-of-bounds coordinates (0, 4)")
        assertFalse(board.hasTile(-4, 0), "hasTile should return false for out-of-bounds coordinates (-4, 0)")
        assertFalse(board.hasTile(0, -4), "hasTile should return false for out-of-bounds coordinates (0, -4)")
    }

    @Test
    fun testGetTileWhenTileExists() {
        // Add a tile at (0, 0)
        val tile = Tile(8, Resource.WOOD)
        board.addTile(0, 0, tile)

        // Verify that getTile returns the correct tile for (0, 0)
        val retrievedTile = board.getTile(0, 0)
        assertNotNull(retrievedTile, "getTile should return a non-null tile for (0, 0)")
        assertEquals(tile, retrievedTile, "getTile should return the correct tile for (0, 0)")
    }

    @Test
    fun testGetTileWhenTileDoesNotExist() {
        // Verify that getTile returns null for (1, 1) (no tile added here)
        assertNull(board.getTile(1, 1), "getTile should return null for (1, 1)")
    }

    @Test
    fun testGetTileOutOfBounds() {
        // Verify that getTile returns null for out-of-bounds coordinates
        assertNull(board.getTile(4, 0), "getTile should return null for out-of-bounds coordinates (4, 0)")
        assertNull(board.getTile(0, 4), "getTile should return null for out-of-bounds coordinates (0, 4)")
        assertNull(board.getTile(-4, 0), "getTile should return null for out-of-bounds coordinates (-4, 0)")
        assertNull(board.getTile(0, -4), "getTile should return null for out-of-bounds coordinates (0, -4)")
    }
}