package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.data.BuildingType
import eric.bitria.hexon.src.data.Resource
import org.junit.jupiter.api.Assertions.*
import org.junit.Test

class TileTest {
    @Test
    fun testCreateTile() {
        val board = Board()

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
            assertEquals(BuildingType.NONE, vertex.building)
        }
    }
}