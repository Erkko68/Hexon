package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.utils.sortTriple
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class VertexTest {

    @Test
    fun getAdjacentVertexCoords_ReturnsCorrectAdjacentVertices() {
        // Create a vertex with coordinates (0,0), (1,0), (0,1)
        val axialCoordA = AxialCoord(0, 0)
        val axialCoordB = AxialCoord(1, 0)
        val axialCoordC = AxialCoord(0, 1)
        val vertex = Vertex(Triple(axialCoordA, axialCoordB, axialCoordC))

        // Get adjacent vertices' coordinates
        val adjacentVertices = vertex.getAdjacentVerticesCoords()

        // Expected adjacent vertices (sorted)
        val expected1 = sortTriple(Triple(axialCoordA, axialCoordB, AxialCoord(1, -1)))
        val expected2 = sortTriple(Triple(axialCoordB, axialCoordC, AxialCoord(1, 1)))
        val expected3 = sortTriple(Triple(axialCoordA, axialCoordC, AxialCoord(-1, 1)))

        // Verify the results
        assertEquals(3, adjacentVertices.size)
        assertTrue(adjacentVertices.contains(expected1))
        assertTrue(adjacentVertices.contains(expected2))
        assertTrue(adjacentVertices.contains(expected3))
    }

    @Test
    fun getAdjacentVertexCoords_ReturnsCorrectAdjacentVerticesSecondPosition() {
        // Create a vertex with coordinates (0,0), (1,0), (0,1)
        val axialCoordA = AxialCoord(0, 0)
        val axialCoordB = AxialCoord(1, 0)
        val axialCoordC = AxialCoord(1, -1)
        val vertex = Vertex(Triple(axialCoordA, axialCoordB, axialCoordC))

        // Get adjacent vertices' coordinates
        val adjacentVertices = vertex.getAdjacentVerticesCoords()

        // Expected adjacent vertices (sorted)
        val expected1 = sortTriple(Triple(axialCoordA, axialCoordB, AxialCoord(0, 1)))
        val expected2 = sortTriple(Triple(axialCoordB, axialCoordC, AxialCoord(2, -1)))
        val expected3 = sortTriple(Triple(axialCoordA, axialCoordC, AxialCoord(0, -1)))

        // Verify the results
        assertEquals(3, adjacentVertices.size)
        assertTrue(adjacentVertices.contains(expected1))
        assertTrue(adjacentVertices.contains(expected2))
        assertTrue(adjacentVertices.contains(expected3))
    }
}