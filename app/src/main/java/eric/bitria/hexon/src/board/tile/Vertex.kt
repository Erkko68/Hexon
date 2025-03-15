package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.utils.sortPair
import eric.bitria.hexon.src.utils.sortTriple

class Vertex(
    private val coords: Triple<AxialCoord, AxialCoord, AxialCoord>
) {
    private var building: Building = Building.NONE

    fun getCoords(): Triple<AxialCoord, AxialCoord, AxialCoord> = sortTriple(coords)

    fun hasBuilding() = building != Building.NONE

    fun placeBuilding(building: Building) {
        this.building = building
    }

    /**
     * Returns a list of the three adjacent vertices.
     * Each adjacent vertex is represented as a sorted Triple of Coord.
     */
    fun getAdjacentVerticesCoords(): List<Triple<AxialCoord, AxialCoord, AxialCoord>> {
        val adjacentVertices = mutableListOf<Triple<AxialCoord, AxialCoord, AxialCoord>>()
        val pairsWithThird = listOf(
            Triple(coords.first, coords.second, coords.third),
            Triple(coords.second, coords.third, coords.first),
            Triple(coords.first, coords.third, coords.second)
        )
        for ((a, b, thirdCoord) in pairsWithThird) {
            val direction = a.directionTo(b)
            val c1 = a.getNeighbor(direction.next())
            val c2 = a.getNeighbor(direction.prev())
            val d = when (thirdCoord) {
                c1 -> c2
                c2 -> c1
                else -> {
                    throw IllegalStateException("Invalid vertex configuration")
                }
            }
            val newTriple = sortTriple(Triple(a, b, d))
            adjacentVertices.add(newTriple)
        }
        return adjacentVertices
    }

    fun getAdjacentEdgesCoords(): List<Pair<AxialCoord, AxialCoord>> {
        return listOf(
            sortPair(Pair(coords.first, coords.second)),
            sortPair(Pair(coords.second, coords.third)),
            sortPair(Pair(coords.first, coords.third))
        )
    }

    companion object {
        // The same method as before but static
        fun getAdjacentEdgesCoords(vertexCoord: Triple<AxialCoord, AxialCoord, AxialCoord>): List<Pair<AxialCoord, AxialCoord>> {
            return listOf(
                sortPair(Pair(vertexCoord.first, vertexCoord.second)),
                sortPair(Pair(vertexCoord.second, vertexCoord.third)),
                sortPair(Pair(vertexCoord.first, vertexCoord.third))
            )
        }
    }
}
