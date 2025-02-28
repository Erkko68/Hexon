package eric.bitria.hexon.src.board

import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.data.BuildingType
import eric.bitria.hexon.src.data.Resource
import org.junit.jupiter.api.Assertions.*
import org.junit.Test

class BoardTest{

    @Test
    fun testLinkTiles() {
        val board = Board()

        // Create two tiles with specific resources and numbers
        val tile1 = Tile(8, Resource.WOOD)
        val tile2 = Tile(10, Resource.BRICK)

        // Link tile2 to tile1 at edge 0
        board.linkTiles(tile1, tile2, 0)

        // Check that tile2 is correctly linked to tile1 on edge 0
        assertEquals(tile1.edges[0], tile2.edges[3]) // tile1's edge 0 should link to tile2's edge 3

        // Check that the vertices of tile2 are correctly set to tile1's adjacent vertices
        assertEquals(tile1.vertices[0], tile2.vertices[2]) // tile1 vertex 5 links to tile2 vertex 2
        assertEquals(tile1.vertices[5], tile2.vertices[3]) // tile1 vertex 0 links to tile2 vertex 3

        // Check that tile1's edge 0 has tile2 as its second neighbor
        assertEquals(tile2, tile1.edges[0].tiles[1])
    }
}