package eric.bitria.hexon.view

import eric.bitria.hexon.src.player.Player

data class TurnManager(
    val players: List<Player>,
    val onRotationComplete: (() -> Unit) = {}
) {
    private var currentTurnIndex = 0

    val turnOrder: List<Player> = players.shuffled()

    fun getCurrentPlayer(): Player = turnOrder[currentTurnIndex]

    fun nextTurn() {
        currentTurnIndex++

        // Check if we've reached the end of the rotation
        if (currentTurnIndex >= turnOrder.size) {
            currentTurnIndex = 0
            onRotationComplete.invoke()
        }
    }
}
