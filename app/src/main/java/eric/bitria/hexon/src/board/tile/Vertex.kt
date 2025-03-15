package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.Building
import eric.bitria.hexon.src.data.Coord
import eric.bitria.hexon.src.data.Direction

class Vertex(
    val coords: Triple<Coord, Coord, Coord>
) {
    private var building: Building = Building.NONE

    fun hasBuilding() = building != Building.NONE

    fun placeBuilding(building: Building) {
        this.building = building
    }

    /**
     * Returns a list of the three adjacent vertices.
     * Each adjacent vertex is represented as a sorted Triple of Coord.
     */
    fun getAdjacentVertexTriplets(): List<Triple<Coord, Coord, Coord>> {
        val adjacentVertices = mutableListOf<Triple<Coord, Coord, Coord>>()
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

    private fun sortTriple(triple: Triple<Coord, Coord, Coord>): Triple<Coord, Coord, Coord> {
        return listOf(triple.first, triple.second, triple.third).sorted().let {
            Triple(it[0], it[1], it[2])
        }
    }
}
