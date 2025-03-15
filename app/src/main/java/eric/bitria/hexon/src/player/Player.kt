package eric.bitria.hexon.src.player

import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Resource

class Player {
    private var victoryPoints: Int = 0
    private val buildings: MutableList<Vertex> = mutableListOf()
    private val roads: MutableList<Edge> = mutableListOf()

    private val deck: Deck = Deck()

    /**
     * Adds a resource to the player's deck.
     */
    fun addResource(resource: Resource){
        deck.addResource(resource)
    }

    fun getVictoryPoints(): Int {
        return victoryPoints
    }
}