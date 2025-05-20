package eric.bitria.hexon.view.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import eric.bitria.hexon.ui.screen.GameSettings

class GameSettingsViewModel {
    var settings by mutableStateOf(GameSettings())
        private set

    fun updatePlayerName(name: String) {
        settings = settings.copy(playerName = name)
    }

    fun updatePlayerColor(color: Color) {
        settings = settings.copy(playerColor = color)
    }

    fun updateNumberOfBots(bots: Int) {
        settings = settings.copy(numberOfBots = bots)
    }

    fun updateVictoryPoints(points: Int) {
        settings = settings.copy(victoryPoints = points)
    }

    fun updateTimer(timer: Long) {
        settings = settings.copy(timer = timer)
    }

    fun updateGameSettings(newSettings: GameSettings) {
        settings = newSettings
    }
}