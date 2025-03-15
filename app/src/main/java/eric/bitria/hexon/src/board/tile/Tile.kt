package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.data.game.Resource

class Tile(
    val coords: AxialCoord,
    val resource: Resource,
    val number: Int
) {
    val edges = mutableMapOf<Direction, Edge>()
    val vertices = mutableMapOf<Direction, Vertex>()
}
