package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.Building
import eric.bitria.hexon.src.data.Coord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.data.Resource
import eric.bitria.hexon.src.exceptions.TileNotAddedException

class Tile(
    val coords: Coord,
    val resource: Resource,
    val number: Int
) {
    val edges = mutableMapOf<Direction, Edge>()
    val vertices = mutableMapOf<Direction, Vertex>()

    fun placeBuilding(building: Building, direction: Direction) {
        val vertex = vertices[direction]
            ?: throw TileNotAddedException(coords, direction)
        vertex.placeBuilding(building)
    }

    fun placeRoad(direction: Direction) {
        val edge = edges[direction]
            ?: throw TileNotAddedException(coords, direction)
        edge.placeBuilding(Building.ROAD)
    }
}
