package eric.bitria.hexon.src.board

import androidx.compose.runtime.mutableStateMapOf
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.exceptions.InvalidBoardException
import eric.bitria.hexon.src.exceptions.InvalidBuildingException
import eric.bitria.hexon.src.exceptions.TileNotAddedException
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.src.utils.sortPair
import eric.bitria.hexon.src.utils.sortTriple
import kotlin.math.abs

// Board follows Axial Coordinate System: https://www.redblobgames.com/grids/hexagons/#coordinates-axial
class Board(val radius: Int) {

    val tiles = mutableStateMapOf<AxialCoord, Tile>()
    val edges = mutableStateMapOf<Pair<AxialCoord, AxialCoord>, Edge>()
    val vertices = mutableStateMapOf<Triple<AxialCoord, AxialCoord, AxialCoord>, Vertex>()

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

    // Setters

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

    fun placeBuilding(vertex: Vertex, building: Building, player: Player) {
        if (!vertices.containsKey(vertex.getCoords())) throw TileNotAddedException("The tile for this vertex has not been added to the board.")
        vertex.placeBuilding(building,player)
    }

    fun placeRoad(edge: Edge, player: Player) {
        if (!edges.containsKey(edge.getCoords())) throw TileNotAddedException("The tile for this edge has not been added to the board.")
        edge.placeBuilding(Building.ROAD, player)
    }

    // Resource

    fun giveResource(number: Int){
        tiles.forEach{ tile ->
            if(tile.value.number == number){
                tile.value.giveResource();
            }
        }
    }

    fun givePlacementResources(vertex: Vertex){
        if(!vertex.hasBuilding()) { throw InvalidBuildingException("This vertex has no building!")}
        vertex.getCoords().toList()
            .forEach{
                getTile(it)?.resource?.let { it1 -> vertex.player?.addResource(it1,1) }
            }
    }

    // Checks

    fun canPlaceBuilding(vertex: Vertex, player: Player): Boolean {
        // Condition 1: Vertex must not have a building
        // Condition 2: No adjacent vertex has a building
        // Condition 3: At least one adjacent edge has a building owned by the player
        return canPlaceBuilding(vertex) &&
                vertex.getAdjacentEdgesCoords().any { edges[it]?.let { edge -> edge.hasBuilding() && edge.player == player } == true }
    }

    // Used only on the first building placement
    fun canPlaceBuilding(vertex: Vertex): Boolean {
        // Condition 1: Vertex must not have a building
        // Condition 2: No adjacent vertex has a building
        return !vertex.hasBuilding() &&
                vertex.getAdjacentVerticesCoords().none { vertices[it]?.hasBuilding() == true }
    }

    fun canPlaceRoad(edge: Edge, player: Player): Boolean {
        // Condition 1: Edge must not have a building
        // Condition 2: At least one adjacent vertex has a building owned by the player
        // Condition 3: At least one adjacent edge has a road owned by the player
        return !edge.hasBuilding() && (
                edge.getAdjacentVerticesCoords().any { vertices[it]?.let {
                    vertex -> vertex.hasBuilding() && vertex.player == player
                } == true } ||
                        edge.getAdjacentEdgesCoords().any { edges[it]?.let {
                            adjacentEdge -> adjacentEdge.hasBuilding() && adjacentEdge.player == player
                        } == true }
                )
    }

    // Helper functions

    private fun isWithinBoard(axialCoord: AxialCoord): Boolean {
        return (abs(axialCoord.q) <= radius + 1) &&
                (abs(axialCoord.r) <= radius + 1) &&
                (abs(axialCoord.q + axialCoord.r) <= radius + 1)
    }

}