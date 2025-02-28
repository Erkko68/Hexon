package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.Resource

data class Tile(
    val number: Int,
    val type: Resource,
    val edges: Array<Edge> = Array(6) { Edge() },
    val vertices: Array<Vertex> = Array(6) { Vertex() }
) {
    init {
        // Place this tile as the first tile on each edge
        edges.forEach { edge ->
            edge.tiles[0] = this
        }
    }
}