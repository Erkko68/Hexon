package eric.bitria.hexon.src.board

import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.exceptions.InvalidBoardException

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BoardTest {

    private lateinit var board: Board

    @Before
    fun setUp() { board = Board(3) }

    @Test
    fun addTile_ShouldStoreTileAndCreateEdgesAndVertices() {
        val axialCoord = AxialCoord(0, 0)
        val tile = Tile(axialCoord, Resource.WOOD, 8)

        board.addTile(tile)

        // Verify the tile is stored
        assertEquals(tile, board.getTile(axialCoord))

        // Verify edges and vertices are created correctly
        Direction.entries.forEach { dir ->
            val neighborCoord = axialCoord.getNeighbor(dir)
            val edgeCoords = listOf(axialCoord, neighborCoord).sorted()
            val edgePair = Pair(edgeCoords[0], edgeCoords[1])

            assertNotNull("Edge should be created for direction $dir", board.getEdge(edgePair.first, edgePair.second))

            val neighborCoord1 = axialCoord.getNeighbor(dir.next())
            val vertexCoords = listOf(axialCoord, neighborCoord, neighborCoord1).sorted()
            val vertexTriple = Triple(vertexCoords[0], vertexCoords[1], vertexCoords[2])

            assertNotNull("Vertex should be created for direction $dir", board.getVertex(vertexTriple.first, vertexTriple.second, vertexTriple.third))
        }
    }

    @Test
    fun getEdge_ShouldReturnCorrectEdge() {
        val axialCoord1 = AxialCoord(0, 0)
        val axialCoord2 = AxialCoord(1, 0)
        val tile = Tile(axialCoord1, Resource.WOOD, 5)

        board.addTile(tile)

        val edge = board.getEdge(axialCoord1, axialCoord2)

        assertNotNull("Edge between $axialCoord1 and $axialCoord2 should exist", edge)
    }

    @Test
    fun getVertex_ShouldReturnCorrectVertex() {
        val axialCoord1 = AxialCoord(0, 0)
        val axialCoord2 = AxialCoord(1, 0)
        val axialCoord3 = AxialCoord(0, 1)
        val tile = Tile(axialCoord1, Resource.WHEAT, 9)

        board.addTile(tile)

        val vertex = board.getVertex(axialCoord1, axialCoord2, axialCoord3)

        assertNotNull("Vertex between $axialCoord1, $axialCoord2, and $axialCoord3 should exist", vertex)
    }

    // Exceptions

    @Test(expected = InvalidBoardException::class)
    fun testGetVertexThrowsException() {
        // Coordinates outside the board's radius
        val coord1 = AxialCoord(4, 0) // Out of bounds
        val coord2 = AxialCoord(0, 0) // Within bounds
        val coord3 = AxialCoord(1, -1) // Within bounds

        board.getVertex(coord1, coord2, coord3)
    }

    @Test(expected = InvalidBoardException::class)
    fun testGetEdgeThrowsException() {
        // Coordinates outside the board's radius
        val coord1 = AxialCoord(-4, 0) // Out of bounds
        val coord2 = AxialCoord(0, 0) // Within bounds

        board.getEdge(coord1, coord2)
    }

    @Test(expected = InvalidBoardException::class)
    fun testGetTileThrowsException() {
        // Coordinate outside the board's radius
        val coord = AxialCoord(0, 4) // Out of bounds

        board.getTile(coord)
    }

}
