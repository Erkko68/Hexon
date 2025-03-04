package eric.bitria.hexon.src.board

import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.exceptions.InvalidBoardException

// Board follows Axial Coordinate System: https://www.redblobgames.com/grids/hexagons/#coordinates-axial
class Board(
    private val maxQ: Int,
    private val maxR: Int
) {
    val tiles: HashMap<Pair<Int, Int>, Tile> = HashMap()

    /**
     * Adds a tile to the board at the given axial coordinates (q, r).
     *
     * @param q Axial coordinate q
     * @param r Axial coordinate r
     * @param tile The tile to place on the board
     */
    fun addTile(q: Int, r: Int, tile: Tile) {
        // Check if the coordinates are within the valid range
        if (q !in -maxQ..maxQ || r !in -maxR..maxR) {
            throw InvalidBoardException("Coordinates ($q, $r) are out of bounds. Valid range for q: [-$maxQ, $maxQ], r: [-$maxR, $maxR]")
        }

        // Define the surrounding coordinates
        val surroundingCoordinates = listOf(
            Pair(q + 1, r - 1),   // Top-right
            Pair(q + 1, r),       // Right
            Pair(q, r + 1),       // Bottom-right
            Pair(q - 1, r + 1),   // Bottom-left
            Pair(q - 1, r),       // Left
            Pair(q, r - 1)        // Top-left
        )

        // Check each surrounding tile and link if it exists
        surroundingCoordinates.forEachIndexed { index, cords ->
            val adjacentTile = tiles[cords] ?: return@forEachIndexed // Skip if null
            val oppositeEdge = (index + 3) % 6
            adjacentTile.linkTile(tile, oppositeEdge)
        }

        tiles[Pair(q, r)] = tile
    }


    /**
     * Retrieves a tile from the board at the given coordinates.
     *
     * @param q Axial coordinate q
     * @param r Axial coordinate r
     * @return The tile at (q, r), or null if no tile is present.
     */
    fun getTile(q: Int, r: Int): Tile? {
        if (q !in -maxQ..maxQ || r !in -maxR..maxR) {
            throw InvalidBoardException("Coordinates ($q, $r) are out of bounds. Valid range for q: [-$maxQ, $maxQ], r: [-$maxR, $maxR]")
        }
        return tiles[Pair(q, r)]
    }

    /**
     * Checks if a tile exists at the given coordinates.
     *
     * @param q Axial coordinate q
     * @param r Axial coordinate r
     * @return True if a tile exists at (q, r), false otherwise.
     */
    fun hasTile(q: Int, r: Int): Boolean {
        if (q !in -maxQ..maxQ || r !in -maxR..maxR) {
            throw InvalidBoardException("Coordinates ($q, $r) are out of bounds. Valid range for q: [-$maxQ, $maxQ], r: [-$maxR, $maxR]")
        }
        return tiles.containsKey(Pair(q, r))
    }
}
