package eric.bitria.hexon.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.view.enums.GamePhase
import kotlin.random.Random

class GameManager(
    private val board: Board,
    private val currentPlayer: Player
) {
    private var _dice by mutableIntStateOf(0)
    fun getDice(): Int = _dice

    // Roll Dice (Global)

    fun rollDice() {
        val dice1 = Random.nextInt(1, 7)
        val dice2 = Random.nextInt(1, 7)
        _dice = dice1 + dice2
        // Give resources
        board.giveResource(_dice)
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