package eric.bitria.hexon.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.player.Player
import kotlin.random.Random

class GameManager(
    private val board: Board,
    private var currentPlayer: Player
) {
    private var _dice1 by mutableIntStateOf(0)
    private var _dice2 by mutableIntStateOf(0)

    fun getDices(): Pair<Int, Int> = Pair(_dice1, _dice2)
    fun setCurrentPlayer(player: Player) { currentPlayer = player}

    // Roll Dice (Global)
    fun rollDice() {
        _dice1 = Random.nextInt(1, 7)
        _dice2 = Random.nextInt(1, 7)
        // Give resources
        board.giveResource(_dice1 + _dice2)
    }

    // Initial Placements

    fun placeInitialSettlement(item: Vertex){
        // Set initial settlement
        board.placeBuilding(item,Building.SETTLEMENT, currentPlayer)
        board.givePlacementResources(item)
    }

    fun placeInitialRoad(item: Edge){
        // Set initial road
        board.placeRoad(item, currentPlayer)
    }

    // Normal Placements

    fun placeSettlement(item: Vertex) {
        board.placeBuilding(item,Building.SETTLEMENT, currentPlayer)
        // Remove deck resources
        currentPlayer.removeBuildingResources(Building.SETTLEMENT)
    }

     fun placeRoad(item: Edge) {
        board.placeRoad(item, currentPlayer)
        currentPlayer.removeBuildingResources(Building.ROAD)
    }

}