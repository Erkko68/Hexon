package eric.bitria.hexon.view.utils

import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex

/**
 * Sealed class representing different types of click handlers for various game actions.
 *
 * This class is used to encapsulate different types of click events based on the game context.
 * The `ClickHandler` class helps manage various interactions in the game, with each subclass
 * corresponding to a specific type of interaction that requires a handler function.
 */
sealed class ClickHandler {

    /**
     * Represents a click on a [Vertex].
     *
     * This is used when the player clicks on a [Vertex] in the game (e.g., selecting a location
     * to place a settlement or city).
     *
     * @param handler A function that takes a [Vertex] as a parameter and performs an action.
     */
    data class OnVertex(val handler: (Vertex) -> Unit) : ClickHandler()

    /**
     * Represents a click on an [Edge].
     *
     * This is used when the player clicks on an [Edge] (e.g., selecting a road to place).
     *
     * @param handler A function that takes an [Edge] as a parameter and performs an action.
     */
    data class OnEdge(val handler: (Edge) -> Unit) : ClickHandler()


    data class OnCard(val handler: (CardType) -> Unit) : ClickHandler()

    /**
     * Represents a click action that doesn't require any parameters. (Used for the Dice Roll)
     *
     * @param handler A function that performs an action without any parameters.
     */
    data class NoParam(val handler: () -> Unit) : ClickHandler()

    /**
     * Represents no click action, or an inactive state.
     *
     * This is used to represent a state where no click handler is defined. It's typically used
     * to indicate that clicking isn't currently possible or that no action is associated with
     * the current click event.
     */
    object None : ClickHandler()
}