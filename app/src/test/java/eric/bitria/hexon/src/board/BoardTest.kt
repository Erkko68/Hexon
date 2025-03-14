package eric.bitria.hexon.src.board

import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.data.Coord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.data.Resource

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BoardTest {

    private lateinit var board: Board

    @Before
    fun setUp() {
        board = Board(3) // Create a new board with radius 3 for each test
    }

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
}
