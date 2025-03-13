package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.Resource

class Tile(
    val number: Int,
    val type: Resource,
) {
    private var parent: Tile? = null
    val edges: Array<Edge> = Array(6) { Edge() }
    val vertices: Array<Vertex> = Array(6) { Vertex() }

    init {
        // Place this tile as the first tile on each edge
        edges.forEach { edge ->
            edge.tiles[0] = this
        }

        // Link edges to vertices
        edges.forEachIndexed { index, edge ->
            edge.vertex[0] = vertices[index] // First vertex will be the previous vertex
            edge.vertex[1] = vertices[(index + 1) % 6] // Second vertex will be the next vertex
        }

        // Link vertices to edges
        vertices.forEachIndexed { index, vertex ->
            vertex.edges[0] = edges[(index + 5) % 6] // First edge position will be the previous edge
            vertex.edges[1] = edges[index] // Second edge position will be the next edge
            vertex.edges[2] = null; // Third edge will be the outgoing edge, only set when joining tiles.
        }
    }

    /**
     * Links two tiles together, where tile is placed around the referenced tile on a specified edge.
     *
     * @param tile The tile being placed around the referenced tile.
     * @param edgeIndex The index of the edge in this where tile will be placed (from 0 to 5).
     *
     * This function will:
     * - Place tile adjacent to this tile at the specified edge of tile1. (Tile edge will be replaced with this edge)
     * - Replace the references from tile edges and vertices with this edge and vertices. (Tile vertices will be replaced with this vertices)
     */
    fun linkTile(tile: Tile, edgeIndex: Int) {
        if (edgeIndex !in 0..5) throw IllegalArgumentException("Edge index must be between 0 and 5.")

        // Get the opposite edge index
        val oppositeEdgeIndex = (edgeIndex + 3) % 6
        val oppositeVertexIndex = (edgeIndex + 4) % 6

        // Set tile1's edge into tile2's opposite edge
        tile.edges[oppositeEdgeIndex] = this.edges[edgeIndex]

        // Set tile1's vertices into tile2's vertices
        tile.vertices[oppositeVertexIndex] = this.vertices[edgeIndex]
        tile.vertices[(oppositeVertexIndex + 5) % 6] = this.vertices[(edgeIndex + 1) % 6]

        // Set external edge
        if (this.vertices[edgeIndex].edges[2] == null) {
            this.vertices[edgeIndex].edges[2] = tile.edges[(oppositeEdgeIndex + 1) % 6]
        }
        if (this.vertices[(edgeIndex + 1) % 6].edges[2] == null) {
            this.vertices[(edgeIndex + 1) % 6].edges[2] = tile.edges[(oppositeEdgeIndex + 5) % 6]
        }

        // Set tile1's edge neighbor as tile2
        this.edges[edgeIndex].tiles[1] = tile

        // Set parent tile
        tile.parent = this
    }
}