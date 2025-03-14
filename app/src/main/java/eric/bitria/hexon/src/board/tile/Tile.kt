package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.Coord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.data.Resource

class Tile(
    val coord: Coord,
    val resource: Resource,
    val number: Int
) {
    val edges = mutableMapOf<Direction, Edge>()
    val vertices = mutableMapOf<Direction, Vertex>()
}