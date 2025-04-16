package eric.bitria.hexon.src.player

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource

class Player(
    val color: Color,
    val isBot: Boolean = false
) {
    private var _victoryPoints by mutableIntStateOf(0)
    private val deck: Deck = Deck()
    private val shop: Shop = Shop(deck)

    private val edges: MutableList<Edge> = mutableListOf();
    private val vertices: MutableList<Vertex> = mutableListOf();

    fun getVictoryPoints(): Int = _victoryPoints
    fun addVictoryPoints(points: Int) {_victoryPoints += points}

    fun getEdges(): List<Edge> = edges
    fun getVertices(): List<Vertex> = vertices

    fun addEdge(edge: Edge) { edges.add(edge) }
    fun addVertex(vertex: Vertex) { vertices.add(vertex) }

    /**
     * Adds a resource to the player's deck.
     */
    fun addResource(resource: Resource, amount: Int){
        deck.addResource(resource,amount)
    }

    /**
     * Removes a resource from the player's deck.
     */
    fun removeBuildingResources(building: Building) {
        deck.removeBuildingResources(building)
    }

    /**
     * Checks if the player has enough resources to build the specified building.
     */
    fun hasBuildingResources(building: Building): Boolean {
        return deck.hasBuildingResources(building)
    }

    fun getDeckResources(): Map<Resource, Int> {
        return deck.getResources()
    }

    /// Trading functions

    // Getters

    fun getSystemTradeDeckResources(): Map<Resource, Int> { return shop.getSystemTradeDeckResources() }

    fun getPlayerTradeDeckResources(): Map<Resource, Int> { return shop.getPlayerTradeDeckResources() }


    fun selectTradingResource(resource: Resource){
        shop.selectTradingResource(resource)
    }

    fun addTradingResource(resource: Resource){
        shop.addTradingResource(resource)
    }

    fun canAcceptTrade(): Boolean{
        return shop.canAcceptTrade()
    }

    fun cancelTrade(){
        shop.cancelTrade()
    }

    fun acceptTrade() {
        shop.acceptTrade()
    }
}