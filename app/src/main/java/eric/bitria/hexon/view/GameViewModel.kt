package eric.bitria.hexon.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.player.Player

class GameViewModel : ViewModel() {

    val player by mutableStateOf(Player(Color(0xFFFF5722)))
    val board by mutableStateOf(createInitialBoard())

    private fun createInitialBoard(): Board {

        player.deck.addResource(Resource.WOOD, 3)
        player.deck.addResource(Resource.BRICK, 1)
        player.deck.addResource(Resource.ORE, 1)
        player.deck.addResource(Resource.SHEEP, 1)
        player.deck.addResource(Resource.WHEAT, 1)

        return Board(radius = 2).apply {
            // Add tiles
            addTile(Tile(AxialCoord(0, 0), Resource.WOOD, 8))
            addTile(Tile(AxialCoord(0, 1), Resource.BRICK, 8))
            addTile(Tile(AxialCoord(-1, 1), Resource.ORE, 8))
            addTile(Tile(AxialCoord(-2, 0), Resource.WHEAT, 8))
            addTile(Tile(AxialCoord(2, 0), Resource.WHEAT, 8))
            addTile(Tile(AxialCoord(1, 0), Resource.NONE, 8))
            addTile(Tile(AxialCoord(1, -1), Resource.SHEEP, 8))

            addTile(Tile(AxialCoord(1, 1), Resource.BRICK, 8))


            // Place initial buildings
            placeInitialBuildings(player)
        }
    }

    // Place initial settlements and roads
    private fun Board.placeInitialBuildings(player: Player) {
        getTile(AxialCoord(0, 0))?.let { tile ->
            tile.vertices[Direction.SOUTHEAST]?.placeBuilding(Building.SETTLEMENT, player)
        }

        getTile(AxialCoord(2, 0))?.let { tile ->
            tile.vertices[Direction.NORTHEAST]?.placeBuilding(Building.SETTLEMENT, player)
            tile.edges[Direction.NORTHEAST]?.placeBuilding(Building.ROAD, player)
            tile.edges[Direction.EAST]?.placeBuilding(Building.ROAD, player)
        }

        getTile(AxialCoord(1, 1))?.let { tile ->
            tile.vertices[Direction.NORTHWEST]?.placeBuilding(Building.SETTLEMENT, player)
        }
    }
}