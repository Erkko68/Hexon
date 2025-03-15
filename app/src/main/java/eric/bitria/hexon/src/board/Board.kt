package eric.bitria.hexon.src.board

import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.Coord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.exceptions.InvalidBoardException
import kotlin.math.abs

// Board follows Axial Coordinate System: https://www.redblobgames.com/grids/hexagons/#coordinates-axial
class Board(private val radius: Int) {
    private val tiles = mutableMapOf<Coord, Tile>()
    private val edges = mutableMapOf<Pair<Coord, Coord>, Edge>()
    private val vertices = mutableMapOf<Triple<Coord, Coord, Coord>, Vertex>()

    // Getters

    fun getTiles(): List<Tile> = tiles.values.toList()

    fun getVertices(): List<Vertex> = vertices.values.toList()

    fun getEdges(): List<Edge> = edges.values.toList()

    fun getVertex(coord1: Coord, coord2: Coord, coord3: Coord): Vertex? {
        val vertexCoords = listOf(coord1, coord2, coord3).sorted()
        if (vertexCoords.any { !isWithinBoard(it) }) throw InvalidBoardException("One or more vertex coordinates are out of range.")
        val vertexTriple = Triple(vertexCoords[0], vertexCoords[1], vertexCoords[2])
        return vertices[vertexTriple]
    }

    fun getEdge(coord1: Coord, coord2: Coord): Edge? {
        val edgeCoords = listOf(coord1, coord2).sorted()
        if (edgeCoords.any { !isWithinBoard(it) }) throw InvalidBoardException("One or more edge coordinates are out of range.")
        val edgePair = Pair(edgeCoords[0], edgeCoords[1])
        return edges[edgePair]
    }

    fun getTile(tileCoord: Coord): Tile? {
        if (!isWithinBoard(tileCoord)) throw InvalidBoardException(tileCoord, radius)
        return tiles[tileCoord]
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

            val edgeCoords = listOf(coord, neighborCoord).sorted() // Sort edge coordinates to ensure consistent keys
            val edgePair = Pair(edgeCoords[0], edgeCoords[1])
            val edge = edges.getOrPut(edgePair) { Edge(edgePair) } // Store new edge

            // Get second vertex neighbor coordinate
            val neighborCoord1 = coord.getNeighbor(dir.prev())

            val vertexCoords = listOf(coord, neighborCoord, neighborCoord1).sorted() // Sort vertex coordinates to ensure consistent keys
            val vertexTriple = Triple(vertexCoords[0], vertexCoords[1], vertexCoords[2])
            val vertex = vertices.getOrPut(vertexTriple) { Vertex(vertexTriple) }

            tile.edges[dir] = edge
            tile.vertices[dir] = vertex
        }
    }

    fun canPlaceBuilding(vertex: Vertex): Boolean {
        // Check if any adjacent vertex has a building or if this vertex itself has a building
        if (!vertices.containsKey(vertex.coords)) throw InvalidBoardException("Vertex is not on the board.")
        return !vertex.hasBuilding() &&
                vertex.getAdjacentVertexTriplets().none { adjacentTriplet ->
                    vertices[adjacentTriplet]?.hasBuilding() == true
                }
    }

    // Helper functions

    private fun isWithinBoard(coord: Coord): Boolean {
        return (abs(coord.q) <= radius) &&
                (abs(coord.r) <= radius) &&
                (abs(coord.q + coord.r) <= radius)
    }

}