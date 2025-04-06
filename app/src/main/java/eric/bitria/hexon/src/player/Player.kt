package eric.bitria.hexon.src.player

import androidx.compose.ui.graphics.Color
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Resource

class Player(
    val color: Color
) {
    private var victoryPoints: Int = 0
    private val buildings: MutableList<Vertex> = mutableListOf()
    private val roads: MutableList<Edge> = mutableListOf()

    val deck: Deck = Deck()

    /**
     * Adds a resource to the player's deck.
     */
    fun addResource(resource: Resource, amount: Int){
        deck.addResource(resource,amount)
    }

    fun getVictoryPoints(): Int {
        return victoryPoints
    }
}