package eric.bitria.hexon.src.board

import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.Coord
import eric.bitria.hexon.src.data.Direction

// Board follows Axial Coordinate System: https://www.redblobgames.com/grids/hexagons/#coordinates-axial
class Board(private val radius: Int) {
    val tiles = mutableMapOf<Coord, Tile>()
    private val edges = mutableMapOf<Pair<Coord, Coord>, Edge>()
    private val vertices = mutableMapOf<Triple<Coord, Coord, Coord>, Vertex>()

    fun addTile(tile: Tile) {
        // Get tile coordinates
        val coord = tile.coord

        // Store tile
        tiles[coord] = tile

        // Create or get edges and vertices for the tile
        Direction.entries.forEach { dir ->
            // Get the neighboring coordinate in the specified direction
            val neighborCoord = coord.getNeighbor(dir)

            val edgeCoords = listOf(coord, neighborCoord).sorted() // Sort edge coordinates to ensure consistent keys
            val edgePair = Pair(edgeCoords[0], edgeCoords[1])
            val edge = edges.getOrPut(edgePair) { Edge() } // Store new edge

            // Get second vertex neighbor coordinate
            val neighborCoord1 = coord.getNeighbor(dir.next())

            val vertexCoords = listOf(coord, neighborCoord, neighborCoord1).sorted() // Sort vertex coordinates to ensure consistent keys
            val vertexTriple = Triple(vertexCoords[0], vertexCoords[1], vertexCoords[2])
            val vertex = vertices.getOrPut(vertexTriple) { Vertex() }

            tile.edges[dir] = edge
            tile.vertices[dir] = vertex
        }
    }

    //fun canPlaceBuilding(coord: Coord, direction: Direction): Boolean {}

    fun getVertex(coord1: Coord, coord2: Coord, coord3: Coord): Vertex?{
        val vertexCoords = listOf(coord1, coord2, coord3).sorted()
        val vertexTriple = Triple(vertexCoords[0], vertexCoords[1], vertexCoords[2])
        return vertices[vertexTriple]
    }

    fun getEdge(coord1: Coord, coord2: Coord): Edge?{
        val edgeCoords = listOf(coord1, coord2).sorted()
        val edgePair = Pair(edgeCoords[0], edgeCoords[1])
        return edges[edgePair]
    }
}