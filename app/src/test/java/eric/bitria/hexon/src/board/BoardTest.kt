package eric.bitria.hexon.src.board

import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.data.Resource
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue

class BoardTest {

    @Test
    fun testAddTile() {
        val board = Board()

        // Create two tiles
        val tile1 = Tile(8,Resource.WOOD)
        val tile2 = Tile(5,Resource.BRICK)

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
        val board = Board()

        // Create two tiles
        val tile1 = Tile(8,Resource.WOOD)
        val tile2 = Tile(5,Resource.BRICK)
        val tile3 = Tile(4,Resource.SHEEP)

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
        val tile1LeftEdge = tile1.edges[4]  // Right edge of tile1

        val tile2Edge = tile2.edges[4]  // Left edge of tile2 (opposite edge)

        val tile3Edge = tile3.edges[1]  // Left edge of tile2 (opposite edge)

        assertSame(tile1RightEdge, tile2Edge, "Tile1's right edge should be the same as Tile2's left edge")
        assertSame(tile1LeftEdge, tile3Edge, "Tile1's left edge should be the same as Tile3's right edge")
    }
}
