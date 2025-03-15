package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.src.utils.sortPair
import eric.bitria.hexon.src.utils.sortTriple

class Vertex(
    private val coords: Triple<AxialCoord, AxialCoord, AxialCoord>
) {
    private var building: Building = Building.NONE
    private var player: Player? = null

    fun getCoords(): Triple<AxialCoord, AxialCoord, AxialCoord> = sortTriple(coords)

    fun hasBuilding() = building != Building.NONE

    fun getPlayer() = player

    fun placeBuilding(building: Building, player: Player) {
        this.player = player
        this.building = building
    }

    /**
     * An adjacent vertex is defined as the opposite vertex from an adjacent edge.
     * Since each vertex is connected to 3 edges, we have 3 opposite vertices and therefore adjacent vertices.
     *
     * @return A list containing Triples of the adjacent Vertices.
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

    /**
     * Returns a list of the adjacent edges from this given vertex.
     *
     * @return a list containing Pairs of AxialCoords
     */
    fun getAdjacentEdgesCoords(): List<Pair<AxialCoord, AxialCoord>> {
        return getAdjacentEdgesCoords(coords)
    }

    companion object {
        fun getAdjacentEdgesCoords(vertexCoord: Triple<AxialCoord, AxialCoord, AxialCoord>): List<Pair<AxialCoord, AxialCoord>> {
            return listOf(
                sortPair(Pair(vertexCoord.first, vertexCoord.second)),
                sortPair(Pair(vertexCoord.second, vertexCoord.third)),
                sortPair(Pair(vertexCoord.first, vertexCoord.third))
            )
        }
    }
}
