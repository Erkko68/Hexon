package eric.bitria.hexon.view

import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.player.Player
import kotlin.random.Random

object GameManager {

    // Roll Dice (Global)
    fun rollDice(board: Board): Pair<Int, Int> {
        val newDice1 = Random.nextInt(1, 7)
        val newDice2 = Random.nextInt(1, 7)
        board.giveResource(newDice1 + newDice2)
        return Pair(newDice1, newDice2)
    }

    // Initial Placements
    fun placeInitialSettlement(board: Board, currentPlayer: Player, item: Vertex) {
        board.placeBuilding(item, Building.SETTLEMENT, currentPlayer)
        currentPlayer.addVictoryPoints(1)
        board.givePlacementResources(item)
    }

    fun placeInitialRoad(board: Board, currentPlayer: Player, item: Edge) {
        board.placeRoad(item, currentPlayer)
    }

    // Normal Placements
    fun placeSettlement(board: Board, currentPlayer: Player, item: Vertex) {
        board.placeBuilding(item, Building.SETTLEMENT, currentPlayer)
        currentPlayer.addVictoryPoints(1)
        currentPlayer.removeBuildingResources(Building.SETTLEMENT)
    }

    fun placeCity(board: Board, currentPlayer: Player, item: Vertex) {
        board.placeBuilding(item, Building.CITY, currentPlayer)
        currentPlayer.addVictoryPoints(1)
        currentPlayer.removeBuildingResources(Building.CITY)
    }

    fun placeRoad(board: Board, currentPlayer: Player, item: Edge) {
        board.placeRoad(item, currentPlayer)
        currentPlayer.removeBuildingResources(Building.ROAD)
    }
}
