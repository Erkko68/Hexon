package eric.bitria.hexon.view.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eric.bitria.hexon.persistent.datastore.GameSettingsKeys
import eric.bitria.hexon.ui.screen.GameSettings
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameSettingsViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    var settings by mutableStateOf(GameSettings())
        private set

    init {
        viewModelScope.launch {
            dataStore.data
                .map { preferences ->
                    GameSettings(
                        playerName = preferences[GameSettingsKeys.PLAYER_NAME] ?: "",
                        playerColor = Color(preferences[GameSettingsKeys.PLAYER_COLOR] ?: Color.Blue.toArgb()),
                        numberOfBots = preferences[GameSettingsKeys.NUMBER_OF_BOTS] ?: 1,
                        victoryPoints = preferences[GameSettingsKeys.VICTORY_POINTS] ?: 10,
                        timer = preferences[GameSettingsKeys.TIMER] ?: 60000L
                    )
                }
                .collect { loadedSettings ->
                    settings = loadedSettings
                }
        }
    }

    private suspend fun save(block: MutablePreferences.() -> Unit) {
        dataStore.edit(block)
    }

    fun updatePlayerName(name: String) {
        settings = settings.copy(playerName = name)
        viewModelScope.launch {
            save { this[GameSettingsKeys.PLAYER_NAME] = name }
        }
    }

    fun updatePlayerColor(color: Color) {
        settings = settings.copy(playerColor = color)
        viewModelScope.launch {
            save { this[GameSettingsKeys.PLAYER_COLOR] = color.toArgb() }
        }
    }

    fun updateNumberOfBots(bots: Int) {
        settings = settings.copy(numberOfBots = bots)
        viewModelScope.launch {
            save { this[GameSettingsKeys.NUMBER_OF_BOTS] = bots }
        }
    }

    fun updateVictoryPoints(points: Int) {
        settings = settings.copy(victoryPoints = points)
        viewModelScope.launch {
            save { this[GameSettingsKeys.VICTORY_POINTS] = points }
        }
    }

    fun updateTimer(timer: Long) {
        settings = settings.copy(timer = timer)
        viewModelScope.launch {
            save { this[GameSettingsKeys.TIMER] = timer }
        }
    }

    fun updateGameSettings(newSettings: GameSettings) {
        settings = newSettings
        viewModelScope.launch {
            save {
                this[GameSettingsKeys.PLAYER_NAME] = newSettings.playerName
                this[GameSettingsKeys.PLAYER_COLOR] = newSettings.playerColor.toArgb()
                this[GameSettingsKeys.NUMBER_OF_BOTS] = newSettings.numberOfBots
                this[GameSettingsKeys.VICTORY_POINTS] = newSettings.victoryPoints
                this[GameSettingsKeys.TIMER] = newSettings.timer
            }
        }
    }
}
