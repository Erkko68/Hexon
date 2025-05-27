package eric.bitria.hexon.view.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import eric.bitria.hexon.persistent.datastore.PreferencesKeys
import eric.bitria.hexon.ui.screen.GameSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GameSettingsViewModel(
    private val dataStore: DataStore<Preferences>,
    private val scope: CoroutineScope
) {
    var settings by mutableStateOf(GameSettings())
        private set

    init {
        loadSettings()
    }

    private fun loadSettings() {
        scope.launch {
            dataStore.data.collect { preferences ->
                settings = GameSettings(
                    playerName = preferences[PreferencesKeys.PLAYER_NAME] ?: "",
                    playerColor = preferences[PreferencesKeys.PLAYER_COLOR]?.let { Color(it) } ?: Color.Blue,
                    numberOfBots = preferences[PreferencesKeys.NUMBER_OF_BOTS] ?: 1,
                    victoryPoints = preferences[PreferencesKeys.VICTORY_POINTS] ?: 8,
                    timer = preferences[PreferencesKeys.TIMER] ?: 60000L
                )
            }
        }
    }

    // Update functions call saveSettings()
    fun updatePlayerName(name: String) {
        settings = settings.copy(playerName = name)
        saveSettings()
    }

    fun updatePlayerColor(color: Color) {
        settings = settings.copy(playerColor = color)
        saveSettings()
    }

    fun updateNumberOfBots(bots: Int) {
        settings = settings.copy(numberOfBots = bots)
        saveSettings()
    }

    fun updateVictoryPoints(points: Int) {
        settings = settings.copy(victoryPoints = points)
        saveSettings()
    }

    fun updateTimer(timer: Long) {
        settings = settings.copy(timer = timer)
        saveSettings()
    }

    private fun saveSettings() {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.PLAYER_NAME] = settings.playerName
                preferences[PreferencesKeys.PLAYER_COLOR] = settings.playerColor.toArgb().toLong()
                preferences[PreferencesKeys.NUMBER_OF_BOTS] = settings.numberOfBots
                preferences[PreferencesKeys.VICTORY_POINTS] = settings.victoryPoints
                preferences[PreferencesKeys.TIMER] = settings.timer
            }
        }
    }
}