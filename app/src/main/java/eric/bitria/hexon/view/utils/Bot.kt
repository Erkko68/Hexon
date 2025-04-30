package eric.bitria.hexon.view.utils

import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.view.utils.heuristics.GreedyHeuristic

object Bot{

    fun initialSettlementPlacement(board: Board, player: Player){
        val vertex = GreedyHeuristic.bestInitialSettlement(board, player)
        if (vertex != null) {
            board.placeBuilding(vertex, Building.SETTLEMENT,player)
            player.addVictoryPoints(1)
            player.addVertex(vertex)
        }
    }

    fun placeRoad(board: Board, player: Player){
        val edge = GreedyHeuristic.bestRoadPlacement(board, player)
        if (edge != null) {
            board.placeRoad(edge,player)
            player.addEdge(edge)
        }
    }

    fun placeSettlement(board: Board, player: Player){
        val vertex = GreedyHeuristic.bestSettlementPlacement(board, player)
        if (vertex != null) {
            board.placeBuilding(vertex, Building.SETTLEMENT,player)
            player.addVictoryPoints(1)
            player.addVertex(vertex)
        }
    }
}