package eric.bitria.hexon.view.utils.heuristics

import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.player.Player

object GreedyHeuristic {

    /**
     * Basic probability weights for tile numbers.
     * Numbers 6 and 8 are the most common, whereas 2 and 12 are rare.
     */
    private val diceProbability: Map<Int, Int> = mapOf(
        2 to 1, 3 to 2, 4 to 3, 5 to 4, 6 to 5,
        8 to 5, 9 to 4, 10 to 3, 11 to 2, 12 to 1
    )

    // Constants used for weighting diversity and missing resources.
    private const val DIVERSITY_WEIGHT = 3.0
    private const val MISSING_RESOURCE_BONUS = 5.0

    /**
     * Finds the best vertex for an initial settlement placement.
     *
     * This method filters all vertices that are valid for building (using the board's
     * canPlaceBuilding(vertex) method) and then uses evaluateInitialVertex to choose the best one.
     *
     * @param board The game board.
     * @param bot The bot Player instance.
     * @return The best Vertex, or null if no valid vertex exists.
     */
    fun bestInitialSettlement(board: Board, bot: Player): Vertex? {
        return board.getVertices()
            .filter { board.canPlaceBuilding(it) }
            .maxByOrNull { evaluateInitialVertex(it, board, bot) }
    }

    /**
     * Finds the best settlement placement during normal gameplay.
     *
     * Uses the board's canPlaceBuilding(vertex, player) (which might check connectivity
     * requirements, among others) and the evaluateVertex heuristic.
     *
     * @param board The game board.
     * @param player The current player.
     * @return The best candidate vertex, or null if none is available.
     */
    fun bestSettlementPlacement(board: Board, player: Player): Vertex? {
        return board.getVertices()
            .filter { board.canPlaceBuilding(it, player) }
            .maxByOrNull { evaluateVertex(it, board) }
    }

    /**
     * Finds the best road placement during normal gameplay.
     *
     * Filters for valid roads using board.canPlaceRoad(edge, player) and then evaluates using evaluateEdge.
     *
     * @param board The game board.
     * @param player The current player.
     * @return The best Edge for road placement, or null if no valid edge exists.
     */
    fun bestRoadPlacement(board: Board, player: Player): Edge? {
        return board.getEdges()
            .filter { board.canPlaceRoad(it, player) }
            .maxByOrNull { evaluateEdge(it, board) }
    }

    /**
     * Evaluation function for a vertex during normal gameplay.
     *
     * This method is similar to evaluateInitialVertex but does not include bonuses
     * for missing resources. You might still wish to incorporate diversity or other
     * strategic factors here.
     */
    private fun evaluateVertex(vertex: Vertex, board: Board): Double {
        var score = 0.0
        // Sum the probabilities from all adjacent non-desert tiles.
        for (coord in vertex.getCoords().toList().filter { board.isWithinBoard(it) }) {
            board.getTile(coord)?.let { tile ->
                if (tile.resource != Resource.NONE) {
                    score += diceProbability[tile.number] ?: 0
                }
            }
        }
        return score
    }

    /**
     * Evaluates an edge for road placement based on adjacent vertices.
     *
     * Assumes that an Edge provides a method getAdjacentVerticesCoords() returning a list of triples for vertices.
     *
     * @param edge The candidate edge.
     * @param board The game board.
     * @return A score representing how valuable that road placement might be.
     */
    private fun evaluateEdge(edge: Edge, board: Board): Double {
        var score = 0.0
        // Evaluate each vertex adjacent to this road.
        for (vertexCoords in edge.getAdjacentVerticesCoords()) {
            board.getVertex(vertexCoords.first, vertexCoords.second, vertexCoords.third)?.let { vertex ->
                if (!vertex.hasBuilding()) {
                    score += evaluateVertex(vertex, board)
                }
            }
        }
        return score
    }

    /**
     * Evaluate a vertex for initial settlement placement.
     *
     * This evaluation first sums the probability weights of each adjacent tile
     * (ignoring deserts). Then it adds bonus points for resource diversity
     * and extra bonus if the bot does not yet have a given resource.
     *
     * @param vertex The candidate vertex.
     * @param board The current game board.
     * @param bot The bot Player instance.
     * @return A heuristic score representing the value of this vertex.
     */
    private fun evaluateInitialVertex(vertex: Vertex, board: Board, bot: Player): Double {
        // Get the map of resources that the bot already has.
        val botResources: Map<Resource, Int> = bot.getDeckResources()

        // Get the list of adjacent tiles (via their axial coordinates)
        val tileCoords = vertex.getCoords()
        val adjacentTiles = tileCoords.toList().filter { board.isWithinBoard(it) }.mapNotNull { board.getTile(it) }

        // Skip desert or tiles that don't produce resources
        val usefulTiles = adjacentTiles.filter { it.resource != Resource.NONE }

        // Sum the probability weight for all adjacent tiles based on their dice numbers.
        val probabilitySum = usefulTiles.sumOf { tile ->
            diceProbability[tile.number] ?: 0
        }

        // Calculate diversity: count the number of distinct resources.
        val distinctResources = usefulTiles.map { it.resource }.toSet()
        val diversityScore = distinctResources.size * DIVERSITY_WEIGHT

        // Calculate bonus for each distinct resource the bot does not have yet.
        val missingBonus = distinctResources.count { botResources.getOrDefault(it, 0) == 0 } * MISSING_RESOURCE_BONUS

        // Final score aggregates probability, diversity and missing-resource bonus
        return probabilitySum + diversityScore + missingBonus
    }

}
