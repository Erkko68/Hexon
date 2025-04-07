package eric.bitria.hexon.view

import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.data.game.Resource

object BoardBuilder {

    fun createInitialBoard(radius: Int = 2): Board {
        val board = Board(radius)

        val coords = generateHexCoords(radius)

        // Predefined resources
        val resources = mutableListOf(
            Resource.WOOD, Resource.WOOD, Resource.WOOD, Resource.WOOD,
            Resource.BRICK, Resource.BRICK, Resource.BRICK,
            Resource.SHEEP, Resource.SHEEP, Resource.SHEEP, Resource.SHEEP,
            Resource.WHEAT, Resource.WHEAT, Resource.WHEAT, Resource.WHEAT,
            Resource.ORE, Resource.ORE, Resource.ORE,
            Resource.NONE // Desert
        ).shuffled().toMutableList() // Convert to MutableList after shuffling

        // Number tokens (desert gets none)
        val numberTokens = mutableListOf(
            2, 3, 3, 4, 4, 5, 5, 6, 6,
            8, 8, 9, 9, 10, 10, 11, 11, 12
        ).shuffled().toMutableList() // Convert to MutableList after shuffling

        coords.forEach { coord ->
            val resource = resources.removeAt(0)
            val number = if (resource == Resource.NONE) 7 else numberTokens.removeAt(0)
            board.addTile(Tile(coord, resource, number))
        }


        return board
    }

    private fun generateHexCoords(radius: Int): List<AxialCoord> {
        val coords = mutableListOf<AxialCoord>()
        for (q in -radius..radius) {
            val r1 = maxOf(-radius, -q - radius)
            val r2 = minOf(radius, -q + radius)
            for (r in r1..r2) {
                coords.add(AxialCoord(q, r))
            }
        }
        return coords.shuffled() // Randomize tile placement
    }
}
