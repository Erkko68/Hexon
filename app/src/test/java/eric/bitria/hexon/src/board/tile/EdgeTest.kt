package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.utils.sortPair
import eric.bitria.hexon.src.utils.sortTriple
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class EdgeTest {
    @Test
    fun getAdjacentVerticesCoords_EASTDirection() {
        // Edge between (0,0) and (1,0) (EAST direction)
        val edge = Edge(Pair(AxialCoord(0, 0), AxialCoord(1, 0)))
        val result = edge.getAdjacentVerticesCoords()

        // Expected vertices
        val expectedTriplet1 = sortTriple(Triple(AxialCoord(0, 0), AxialCoord(1, 0), AxialCoord(0, 1)))
        val expectedTriplet2 = sortTriple(Triple(AxialCoord(0, 0), AxialCoord(1, 0), AxialCoord(1, -1)))

        // Verify that the result contains both expected triplets
        assertTrue(
            "EAST-direction edge should contain $expectedTriplet1",
            result.contains(expectedTriplet1)
        )
        assertTrue(
            "EAST-direction edge should contain $expectedTriplet2",
            result.contains(expectedTriplet2)
        )
    }

    @Test
    fun getAdjacentVerticesCoords_SOUTHEASTDirection() {
        // Edge between (0,0) and (0,1) (SOUTHEAST direction)
        val edge = Edge(Pair(AxialCoord(0, 0), AxialCoord(0, 1)))
        val result = edge.getAdjacentVerticesCoords()

        // Expected vertices
        val expectedTriplet1 = sortTriple(Triple(AxialCoord(-1, 1), AxialCoord(0, 0), AxialCoord(0, 1)))
        val expectedTriplet2 = sortTriple(Triple(AxialCoord(0, 0), AxialCoord(0, 1), AxialCoord(1, 0)))

        // Verify that the result contains both expected triplets
        assertTrue(
            "SOUTHEAST-direction edge should contain $expectedTriplet1",
            result.contains(expectedTriplet1)
        )
        assertTrue(
            "SOUTHEAST-direction edge should contain $expectedTriplet2",
            result.contains(expectedTriplet2)
        )
    }

    @Test
    fun getAdjacentVerticesCoords_SOUTHWESTDirection() {
        // Edge between (0,0) and (-1,1) (SOUTHWEST direction)
        val edge = Edge(Pair(AxialCoord(0, 0), AxialCoord(-1, 1)))
        val result = edge.getAdjacentVerticesCoords()

        // Expected vertices
        val expectedTriplet1 = sortTriple(Triple(AxialCoord(-1, 0), AxialCoord(0, 0), AxialCoord(-1, 1)))
        val expectedTriplet2 = sortTriple(Triple(AxialCoord(0, 0), AxialCoord(-1, 1), AxialCoord(0, 1)))

        // Verify that the result contains both expected triplets
        assertTrue(
            "SOUTHWEST-direction edge should contain $expectedTriplet1",
            result.contains(expectedTriplet1)
        )
        assertTrue(
            "SOUTHWEST-direction edge should contain $expectedTriplet2",
            result.contains(expectedTriplet2)
        )
    }

    @Test
    fun testEdgeAdjacentEdges() {
        // Create an edge between (0,0) and (1,0)
        val edge = Edge(Pair(AxialCoord(0, 0), AxialCoord(1, 0)))

        // Get adjacent edge coordinates
        val adjacentEdges = edge.getAdjacentEdgesCoords().toSet()

        // Expected edges (sorted pairs):
        val expected = listOf(
            Pair(AxialCoord(1, 0), AxialCoord(1, -1)),
            Pair(AxialCoord(1, 0), AxialCoord(0, 1)),
            Pair(AxialCoord(0, 0), AxialCoord(0, 1)),
            Pair(AxialCoord(0, 0), AxialCoord(1, -1))
        ).map { sortPair(it) }.toSet()

        assertEquals(expected, adjacentEdges)
    }
}