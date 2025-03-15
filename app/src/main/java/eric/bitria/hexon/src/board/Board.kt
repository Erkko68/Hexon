package eric.bitria.hexon.src.board

import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.exceptions.InvalidBoardException
import eric.bitria.hexon.src.utils.sortPair
import eric.bitria.hexon.src.utils.sortTriple
import kotlin.math.abs

// Board follows Axial Coordinate System: https://www.redblobgames.com/grids/hexagons/#coordinates-axial
class Board(private val radius: Int) {

    private val tiles = mutableMapOf<AxialCoord, Tile>()
    private val edges = mutableMapOf<Pair<AxialCoord, AxialCoord>, Edge>()
    private val vertices = mutableMapOf<Triple<AxialCoord, AxialCoord, AxialCoord>, Vertex>()

    // Getters

    fun getTiles(): List<Tile> = tiles.values.toList()

    fun getVertices(): List<Vertex> = vertices.values.toList()

    fun getEdges(): List<Edge> = edges.values.toList()

    fun getVertex(axialCoord1: AxialCoord, axialCoord2: AxialCoord, axialCoord3: AxialCoord): Vertex? {
        val vertexTriple = sortTriple(Triple(axialCoord1, axialCoord2, axialCoord3))
        if (vertexTriple.toList().any { !isWithinBoard(it) }) throw InvalidBoardException("One or more vertex coordinates are out of range.")
        return vertices[vertexTriple]
    }

    fun getEdge(axialCoord1: AxialCoord, axialCoord2: AxialCoord): Edge? {
        val edgePair = sortPair(Pair(axialCoord1, axialCoord2))
        if (edgePair.toList().any { !isWithinBoard(it) }) throw InvalidBoardException("One or more edge coordinates are out of range.")
        return edges[edgePair]
    }

    fun getTile(tileAxialCoord: AxialCoord): Tile? {
        if (!isWithinBoard(tileAxialCoord)) throw InvalidBoardException(tileAxialCoord, radius)
        return tiles[tileAxialCoord]
    }

    // Logic

    fun addTile(tile: Tile) {
        // Get tile coordinates
        val coord = tile.coords

        // Previous checks
        if (!isWithinBoard(coord)) throw InvalidBoardException(coord, radius)
        if (tiles.containsKey(coord)) throw InvalidBoardException("Tile at $coord is already placed on the board.")

        // Store tile
        tiles[coord] = tile

        // Create or get edges and vertices for the tile
        Direction.entries.forEach { dir ->
            // Get the neighboring coordinate in the specified direction
            val neighborCoord = coord.getNeighbor(dir)

            val edgePair = sortPair(Pair(coord, neighborCoord)) // Sorted Edge Coords
            val edge = edges.getOrPut(edgePair) { Edge(edgePair) }

            // Get second vertex neighbor coordinate
            val neighborCoord1 = coord.getNeighbor(dir.prev())

            val vertexTriple = sortTriple(Triple(coord, neighborCoord, neighborCoord1)) // Sorted vertexCoords
            val vertex = vertices.getOrPut(vertexTriple) { Vertex(vertexTriple) }

            tile.edges[dir] = edge
            tile.vertices[dir] = vertex
        }
    }

    fun canPlaceBuilding(vertex: Vertex): Boolean {
        // Check if any adjacent vertex has a building or if this vertex itself has a building
        return !vertex.hasBuilding() &&
                vertex.getAdjacentVerticesCoords().none { adjacentTriplet ->
                    vertices[adjacentTriplet]?.hasBuilding() == true
                }
    }

    fun canPlaceRoad(edge: Edge): Boolean {
        // Check if any adjacent vertex has a building or if this vertex itself has a building
        return !edge.hasBuilding() &&
                edge.getAdjacentVerticesCoords().none { adjacentTriplet ->
                    vertices[adjacentTriplet]?.hasBuilding() == true
                } || edge.getAdjacentEdgesCoords().none { adjacentPair ->
    }

    // Helper functions

    private fun isWithinBoard(axialCoord: AxialCoord): Boolean {
        return (abs(axialCoord.q) <= radius) &&
                (abs(axialCoord.r) <= radius) &&
                (abs(axialCoord.q + axialCoord.r) <= radius)
    }

}