package eric.bitria.hexon.src.utils

import eric.bitria.hexon.src.data.AxialCoord
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {

    @Test
    fun testSortTripleWithCoord() {
        // Create an unsorted Triple of Coord
        val triple = Triple(
            AxialCoord(2, 3),
            AxialCoord(1, 2),
            AxialCoord(1, 1)
        )

        // Sort the Triple
        val sortedTriple = sortTriple(triple)

        // Expected sorted Triple
        val expectedTriple = Triple(
            AxialCoord(1, 1),
            AxialCoord(1, 2),
            AxialCoord(2, 3)
        )

        // Verify the result
        assertEquals(expectedTriple, sortedTriple)
    }

    @Test
    fun testSortPairWithCoord() {
        // Create an unsorted Pair of Coord
        val pair = Pair(
            AxialCoord(2, 3),
            AxialCoord(1, 2)
        )

        // Sort the Pair
        val sortedPair = sortPair(pair)

        // Expected sorted Pair
        val expectedPair = Pair(
            AxialCoord(1, 2),
            AxialCoord(2, 3)
        )

        // Verify the result
        assertEquals(expectedPair, sortedPair)
    }

}