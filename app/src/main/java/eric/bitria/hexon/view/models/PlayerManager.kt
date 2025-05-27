package eric.bitria.hexon.view.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.persistent.datastore.GameSettings
import eric.bitria.hexon.view.utils.DeckType

class PlayerManager {
    var humanPlayer by mutableStateOf<Player>(Player("Dummy", Color.Black))
        private set
    var bots by mutableStateOf<List<Player>>(emptyList())
        private set
    var allPlayers by mutableStateOf<List<Player>>(emptyList())
        private set
    var currentPlayer by mutableStateOf<Player>(Player("Dummy", Color.Black))
        private set

    fun setupPlayers(settings: GameSettings) {
        val player = Player(settings.playerName, settings.playerColor)
        humanPlayer = player

        val botColors = listOf(
            Color.Blue, Color.Green, Color.Magenta, Color.Cyan, Color.Red
        ).shuffled()

        bots = List(settings.numberOfBots) { index ->
            Player(
                color = botColors[index % botColors.size],
                isBot = true,
                name = "Bot ${index + 1}"
            )
        }
        allPlayers = listOf(player) + bots
        currentPlayer = Player("Dummy", Color.Black) // Will be set by TurnManager
    }

    fun updateCurrentPlayer(player: Player) {
        currentPlayer = player
    }

    fun getCurrentPlayerVictoryPoints(): Int {
        return currentPlayer.getVictoryPoints()
    }

    fun handleResourceClickForTrade(resource: Resource, deckType: DeckType) { // Assuming Resource type
        currentPlayer.let {
            when (deckType) {
                is DeckType.PlayerDeck -> it.addTradingResource(resource)
                is DeckType.SystemDeck -> it.selectTradingResource(resource)
            }
        }
    }

    fun cancelCurrentPlayerTrade() {
        currentPlayer.cancelTrade()
    }

    fun acceptCurrentPlayerTrade() {
        currentPlayer.acceptTrade()
    }
}