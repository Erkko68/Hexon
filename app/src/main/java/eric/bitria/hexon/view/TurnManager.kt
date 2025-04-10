package eric.bitria.hexon.view

import eric.bitria.hexon.src.player.Player

data class TurnManager(
    val players: List<Player>,
    val onRotationComplete: (() -> Unit) = {}
) {
    private var currentTurnIndex = 0

    // Changed to var so we can reverse it
    var turnOrder: List<Player> = players.shuffled()
        private set

    fun getCurrentPlayer(): Player = turnOrder[currentTurnIndex]

    fun nextTurn() {
        currentTurnIndex++

        // Check if we've reached the end of the rotation
        if (currentTurnIndex >= turnOrder.size) {
            currentTurnIndex = 0
            onRotationComplete.invoke()
        }
    }

    /**
     * Reverses the current turn order and resets the current turn index.
     * Used at the end of an initial placement phase to run the reverse round.
     */
    fun reverseTurnOrder() {
        turnOrder = turnOrder.reversed()
        currentTurnIndex = 0
    }
}
