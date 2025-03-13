package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.Building
import eric.bitria.hexon.src.data.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test

class TileTest {

    private lateinit var tile: Tile

    @Before
    fun setUp() {
        tile = Tile(8, Resource.WOOD)
    }

    @Test
    fun testTileInitialization() {
        assertEquals(8, tile.number)
        assertEquals(Resource.WOOD, tile.type)
    }

    @Test
    fun testTileHasCorrectEdgesAndVertices() {
        assertEquals(6, tile.edges.size)
        assertEquals(6, tile.vertices.size)
    }

    @Test
    fun testEdgesHaveCorrectTileReference() {
        tile.edges.forEach { edge ->
            assertNotNull(edge)
            assertEquals(tile, edge.tiles[0])
        }
    }

    @Test
    fun testVerticesHaveNoBuildingByDefault() {
        tile.vertices.forEach { vertex ->
            assertNotNull(vertex)
            assertEquals(Building.NONE, vertex.building)
        }
    }

    @Test
    fun testEdgesAreCorrectlyLinkedToVertices() {
        tile.edges.forEachIndexed { index, edge ->
            assertEquals(edge.vertex[0], tile.vertices[index])
            assertEquals(edge.vertex[1], tile.vertices[(index + 1) % 6])
        }
    }

    @Test
    fun testVerticesAreCorrectlyLinkedToEdges() {
        tile.vertices.forEachIndexed { index, vertex ->
            assertEquals(vertex.edges[0], tile.edges[(index + 5) % 6])
            assertEquals(vertex.edges[1], tile.edges[index])
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
        assertEquals(tile1.vertices[0], tile2.vertices[4]) // tile1 vertex 5 links to tile2 vertex 2
        assertEquals(tile1.vertices[1], tile2.vertices[3]) // tile1 vertex 0 links to tile2 vertex 3

        // Check that tile1's edge 0 has tile2 as its second neighbor
        assertEquals(tile2, tile1.edges[0].tiles[1])
    }

    // Multiple tiles tests

    @Test
    fun testMultipleLinkTile(){
        val tile1 = Tile(8, Resource.WOOD)
        val tile2 = Tile(10, Resource.BRICK)
        val tile3 = Tile(12, Resource.SHEEP)

        val vertex0 = tile1.vertices[0] // Center vertex

        tile1.linkTile(tile2, 0)
        tile1.linkTile(tile3, 5)
        tile2.linkTile(tile3, 4)

        // All three tiles should share same origin vertex
        assertEquals(vertex0, tile1.vertices[0])
        assertEquals(vertex0, tile2.vertices[4])
        assertEquals(vertex0, tile3.vertices[2])

        // Outgoing edge from vertex[0] from tile1 should be tile2's edge[4]
        assertEquals(tile1.vertices[0].edges[2],tile2.edges[4])
        // Outgoing edge from vertex[5] from tile1 should be tile3's edge[3]
        assertEquals(tile1.vertices[5].edges[2],tile3.edges[3])
    }

    @Test
    fun testRawPathIteration(){
        val tile1 = Tile(8, Resource.WOOD)
        val tile2 = Tile(10, Resource.BRICK)
        val tile3 = Tile(12, Resource.SHEEP)

        tile1.linkTile(tile2, 0)
        tile1.linkTile(tile3, 5)
        tile2.linkTile(tile3, 4)

        val t1v3 = tile1.vertices[3]
        val t1v4 = t1v3.edges[1]!!.vertex[1]
        assertEquals(t1v4, tile1.vertices[4])

        val t1v5 = t1v4!!.edges[1]!!.vertex[1]
        assertEquals(t1v5, tile1.vertices[5])

        val t1v0 = t1v5!!.edges[1]!!.vertex[1]
        assertEquals(t1v0, tile1.vertices[0])

        // Outgoing to tile2
        val t2v5 = t1v0!!.edges[2]!!.vertex[1]
        assertEquals(t2v5, tile2.vertices[5])

        // Outgoing to tile3
        val t3v0 = t2v5!!.edges[2]!!.vertex[0]
        assertEquals(t3v0, tile3.vertices[0])
    }
}