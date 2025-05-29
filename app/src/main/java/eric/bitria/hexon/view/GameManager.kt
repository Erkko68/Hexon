package eric.bitria.hexon.view

import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.view.models.LogManager
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
    fun placeInitialSettlement(board: Board, currentPlayer: Player, item: Vertex, logger: LogManager) {
        board.placeBuilding(item, Building.SETTLEMENT, currentPlayer)
        currentPlayer.countTotalBuildings(Building.SETTLEMENT)
        currentPlayer.addVictoryPoints(1)
        board.givePlacementResources(item)
        logger.addLog("[${currentPlayer.name}] placed settlement at \n$item",currentPlayer.color)
    }

    fun placeInitialRoad(board: Board, currentPlayer: Player, item: Edge, logger: LogManager) {
        currentPlayer.countTotalBuildings(Building.ROAD)
        board.placeRoad(item, currentPlayer)
        logger.addLog("[${currentPlayer.name}] placed road at \n$item",currentPlayer.color)
    }

    // Normal Placements
    fun placeSettlement(board: Board, currentPlayer: Player, item: Vertex, logger: LogManager) {
        currentPlayer.countTotalBuildings(Building.SETTLEMENT)
        board.placeBuilding(item, Building.SETTLEMENT, currentPlayer)
        currentPlayer.addVictoryPoints(1)
        currentPlayer.removeBuildingResources(Building.SETTLEMENT)
        logger.addLog("[${currentPlayer.name}] placed settlement at \n$item",currentPlayer.color)
    }

    fun placeCity(board: Board, currentPlayer: Player, item: Vertex, logger: LogManager) {
        currentPlayer.countTotalBuildings(Building.CITY)
        board.placeBuilding(item, Building.CITY, currentPlayer)
        currentPlayer.addVictoryPoints(1)
        currentPlayer.removeBuildingResources(Building.CITY)
        logger.addLog("[${currentPlayer.name}] placed city at \n$item",currentPlayer.color)
    }

    fun placeRoad(board: Board, currentPlayer: Player, item: Edge, logger: LogManager) {
        currentPlayer.countTotalBuildings(Building.ROAD)
        board.placeRoad(item, currentPlayer)
        currentPlayer.removeBuildingResources(Building.ROAD)
        logger.addLog("[${currentPlayer.name}] placed road at \n$item",currentPlayer.color)
    }
}
