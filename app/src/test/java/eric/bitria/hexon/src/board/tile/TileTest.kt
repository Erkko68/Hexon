package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.Building
import eric.bitria.hexon.src.data.Resource
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull

class TileTest {
    @Test
    fun testCreateTile() {
        // Create a tile with a specific number and resource
        val tile = Tile(8, Resource.WOOD)
        // Verify the tile properties
        assertEquals(8, tile.number)
        assertEquals(Resource.WOOD, tile.type)

        // Check that the tile has 6 edges and 6 vertices
        assertEquals(6, tile.edges.size)
        assertEquals(6, tile.vertices.size)

        // Check that each edge is initialized with the correct tile reference
        tile.edges.forEach { edge ->
            assertNotNull(edge)
            assertEquals(tile, edge.tiles[0])
        }

        // Check that each vertex is initialized with no building (default value is NONE)
        tile.vertices.forEach { vertex ->
            assertNotNull(vertex)
            assertEquals(Building.NONE, vertex.building)
        }
    }

    @Test
    fun testLinkTile() {
        // Create two tiles with specific resources and numbers
        val tile1 = Tile(8, Resource.WOOD)
        val tile2 = Tile(10, Resource.BRICK)

        // Link tile2 to tile1 at edge 0
        tile1.linkTile(tile2, 0)

        // Check that tile2 is correctly linked to tile1 on edge 0
        assertEquals(tile1.edges[0], tile2.edges[3]) // tile1's edge 0 should link to tile2's edge 3

        // Check that the vertices of tile2 are correctly set to tile1's adjacent vertices
        assertEquals(tile1.vertices[0], tile2.vertices[3]) // tile1 vertex 5 links to tile2 vertex 2
        assertEquals(tile1.vertices[1], tile2.vertices[4]) // tile1 vertex 0 links to tile2 vertex 3

        // Check that tile1's edge 0 has tile2 as its second neighbor
        assertEquals(tile2, tile1.edges[0].tiles[1])
    }
}