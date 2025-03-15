package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.exceptions.InvalidBuildingException
import eric.bitria.hexon.src.utils.sortPair
import eric.bitria.hexon.src.utils.sortTriple

// When creating a new Edge the Coord Pair has to be sorted.
class Edge(
    private val coords: Pair<AxialCoord, AxialCoord>
){
    private var building: Building = Building.NONE

    fun getCoords(): Pair<AxialCoord, AxialCoord> = sortPair(coords)

    fun hasBuilding() = building != Building.NONE

    fun placeBuilding(building: Building) {
        if (building != Building.ROAD) throw InvalidBuildingException("An edge can only have a ROAD type building")
        this.building = building
    }

    /**
     * From this edge, it returns the 2 adjacent vertices.
     * @return a list of the three adjacent vertices a Triple of coordinates.
     */
    fun getAdjacentVerticesCoords(): List<Triple<AxialCoord, AxialCoord, AxialCoord>> {
        val a = coords.first
        val b = coords.second

        // Get the direction from a to b
        val dir = a.directionTo(b)

        // Calculate the two possible third coordinates for the vertices
        val nextDir = dir.next()
        val prevDir = dir.prev()
        val thirdCoord1 = a.getNeighbor(nextDir)
        val thirdCoord2 = a.getNeighbor(prevDir)

        // return created and sorted the triples
        return listOf(sortTriple(Triple(a, b, thirdCoord1)), sortTriple(Triple(a, b, thirdCoord2)))
    }

    /**
     * From this edge, it returns the 4 adjacent edges.
     *
     * An adjacent edge is defined as the edges from the adjacent vertices without counting this edge itself.
     * So if each adjacent vertex has three edges, when we take the edges from both vertices and combine them
     * in a list we will have 6 edges. But two of them will be this edge so we can exclude them.
     *
     * @return a list of the two adjacent edges.
     */
    fun getAdjacentEdgesCoords(): List<Pair<AxialCoord, AxialCoord>> {
        val adjacentEdges = mutableSetOf<Pair<AxialCoord, AxialCoord>>()

        // For each vertex, get its adjacent edges and filter out the current edge
        this.getAdjacentVerticesCoords().forEach { vertex ->
            val vertexEdges = Vertex.getAdjacentEdgesCoords(vertex)
            adjacentEdges.addAll(vertexEdges)
        }

        // Remove the current edge and return unique edges
        return adjacentEdges
            .filterNot { it == sortPair(coords) }
            .toList()
    }
}