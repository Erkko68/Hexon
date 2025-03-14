package eric.bitria.hexon.src.board

import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.Coord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.data.Resource

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BoardTest {

    private lateinit var board: Board

    @Before
    fun setUp() { board = Board(3) }

    @Test
    fun addTile_ShouldStoreTileAndCreateEdgesAndVertices() {
        val coord = Coord(0, 0)
        val tile = Tile(coord, Resource.WOOD, 8)

        board.addTile(tile)

        // Verify the tile is stored
        assertEquals(tile, board.tiles[coord])

        // Verify edges and vertices are created correctly
        Direction.entries.forEach { dir ->
            val neighborCoord = coord.getNeighbor(dir)
            val edgeCoords = listOf(coord, neighborCoord).sorted()
            val edgePair = Pair(edgeCoords[0], edgeCoords[1])

            assertNotNull("Edge should be created for direction $dir", board.getEdge(edgePair.first, edgePair.second))

            val neighborCoord1 = coord.getNeighbor(dir.next())
            val vertexCoords = listOf(coord, neighborCoord, neighborCoord1).sorted()
            val vertexTriple = Triple(vertexCoords[0], vertexCoords[1], vertexCoords[2])

            assertNotNull("Vertex should be created for direction $dir", board.getVertex(vertexTriple.first, vertexTriple.second, vertexTriple.third))
        }
    }

    @Test
    fun getEdge_ShouldReturnCorrectEdge() {
        val coord1 = Coord(0, 0)
        val coord2 = Coord(1, 0)
        val tile = Tile(coord1, Resource.WOOD, 5)

        board.addTile(tile)

        val edge = board.getEdge(coord1, coord2)

        assertNotNull("Edge between $coord1 and $coord2 should exist", edge)
    }

    @Test
    fun getVertex_ShouldReturnCorrectVertex() {
        val coord1 = Coord(0, 0)
        val coord2 = Coord(1, 0)
        val coord3 = Coord(0, 1)
        val tile = Tile(coord1, Resource.WHEAT, 9)

        board.addTile(tile)

        val vertex = board.getVertex(coord1, coord2, coord3)

        assertNotNull("Vertex between $coord1, $coord2, and $coord3 should exist", vertex)
    }

    @Test
    fun getAdjacentVertexCoords_ReturnsCorrectAdjacentVertices() {
        // Create a vertex with coordinates (0,0), (1,0), (0,1)
        val coordA = Coord(0, 0)
        val coordB = Coord(1, 0)
        val coordC = Coord(0, 1)
        val vertex = Vertex(Triple(coordA, coordB, coordC))

        // Get adjacent vertices' coordinates
        val adjacentVertices = vertex.getAdjacentVertexTriplets()

        // Expected adjacent vertices (sorted)
        val expected1 = sortTriple(Triple(coordA, coordB, Coord(1, -1)))
        val expected2 = sortTriple(Triple(coordB, coordC, Coord(1, 1)))
        val expected3 = sortTriple(Triple(coordA, coordC, Coord(-1, 1)))

        // Verify the results
        assertEquals(3, adjacentVertices.size)
        assertTrue(adjacentVertices.contains(expected1))
        assertTrue(adjacentVertices.contains(expected2))
        assertTrue(adjacentVertices.contains(expected3))
    }

    @Test
    fun getAdjacentVertexCoords_ReturnsCorrectAdjacentVerticesSecondPosition() {
        // Create a vertex with coordinates (0,0), (1,0), (0,1)
        val coordA = Coord(0, 0)
        val coordB = Coord(1, 0)
        val coordC = Coord(1, -1)
        val vertex = Vertex(Triple(coordA, coordB, coordC))

        // Get adjacent vertices' coordinates
        val adjacentVertices = vertex.getAdjacentVertexTriplets()

        // Expected adjacent vertices (sorted)
        val expected1 = sortTriple(Triple(coordA, coordB, Coord(0, 1)))
        val expected2 = sortTriple(Triple(coordB, coordC, Coord(2, -1)))
        val expected3 = sortTriple(Triple(coordA, coordC, Coord(0, -1)))

        // Verify the results
        assertEquals(3, adjacentVertices.size)
        assertTrue(adjacentVertices.contains(expected1))
        assertTrue(adjacentVertices.contains(expected2))
        assertTrue(adjacentVertices.contains(expected3))
    }

    private fun sortTriple(triple: Triple<Coord, Coord, Coord>): Triple<Coord, Coord, Coord> {
        val sorted = listOf(triple.first, triple.second, triple.third).sorted()
        return Triple(sorted[0], sorted[1], sorted[2])
    }
}
