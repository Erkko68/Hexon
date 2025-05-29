package eric.bitria.hexon.view.utils

import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.view.models.LogManager
import eric.bitria.hexon.view.utils.heuristics.GreedyHeuristic

object Bot{

    fun initialSettlementPlacement(board: Board, player: Player, logger: LogManager){
        val vertex = GreedyHeuristic.bestInitialSettlement(board, player)
        if (vertex != null) {
            board.placeBuilding(vertex, Building.SETTLEMENT,player)
            player.countTotalBuildings(Building.SETTLEMENT)
            player.addVictoryPoints(1)
            player.addVertex(vertex)
            logger.addLog("[${player.name}] placed settlement at \n$vertex",player.color)
        }
    }

    fun placeRoad(board: Board, player: Player, logger: LogManager){
        val edge = GreedyHeuristic.bestRoadPlacement(board, player)
        if (edge != null) {
            board.placeRoad(edge,player)
            player.countTotalBuildings(Building.ROAD)
            player.addEdge(edge)
            logger.addLog("[${player.name}] placed road at \n$edge",player.color)
        }
    }

    fun placeSettlement(board: Board, player: Player, logger: LogManager){
        val vertex = GreedyHeuristic.bestSettlementPlacement(board, player)
        if (vertex != null) {
            board.placeBuilding(vertex, Building.SETTLEMENT,player)
            player.countTotalBuildings(Building.SETTLEMENT)
            player.addVictoryPoints(1)
            player.addVertex(vertex)
            logger.addLog("[${player.name}] placed settlement at \n$vertex",player.color)
        }
    }
}