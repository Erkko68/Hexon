package eric.bitria.hexon.src.board

import eric.bitria.hexon.src.board.tile.Tile

class Board() {

    /**
     * Links two tiles together, where tile2 is placed around tile1 on a specified edge.
     *
     * @param tile1 The center tile that tile2 will be placed around.
     * @param tile2 The tile being placed around tile1.
     * @param edgeIndex The index of the edge in tile1 where tile2 will be placed (from 0 to 5).
     *
     * This function will:
     * - Place tile2 adjacent to tile1 at the specified edge of tile1. (Tile2 edge will be replaced with tile1 edge)
     * - Replace the references from tile2's edges and vertices with tile1's edge and vertices. (Tile2 vertices will be replaced with tile1 vertices)
     */
    fun linkTiles(tile1: Tile, tile2: Tile, edgeIndex: Int) {
        if (edgeIndex !in 0..5) throw IllegalArgumentException("Edge index must be between 0 and 5.")

        // Get the opposite edge index
        val oppositeEdgeIndex = (edgeIndex + 3) % 6
        val oppositeVertexIndex = (edgeIndex + 2) % 6

        // Set tile1's edge into tile2's opposite edge
        tile2.edges[oppositeEdgeIndex] = tile1.edges[edgeIndex]

        // Set tile1's vertices into tile2's vertices
        tile2.vertices[oppositeVertexIndex] = tile1.vertices[edgeIndex]
        tile2.vertices[(oppositeVertexIndex + 1) % 6] = tile1.vertices[(edgeIndex + 5) % 6]

        // Set tile1's edge neighbor as tile2
        tile1.edges[edgeIndex].tiles[1] = tile2
    }

}
