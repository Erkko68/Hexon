package eric.bitria.hexon.view.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import eric.bitria.hexon.persistent.datastore.GameSettings
import eric.bitria.hexon.persistent.datastore.PreferencesKeys
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class GameSettingsViewModel(
    private val dataStore: DataStore<Preferences>,
    private val scope: CoroutineScope
) {
    private val _settings = MutableStateFlow<GameSettings?>(null)
    val settings: StateFlow<GameSettings?> = _settings.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        scope.launch {
            dataStore.data
                .map { preferences ->
                    GameSettings(
                        playerName = preferences[PreferencesKeys.PLAYER_NAME] ?: "Player",
                        playerColor = preferences[PreferencesKeys.PLAYER_COLOR]?.let { Color(it) } ?: Color.Blue,
                        numberOfBots = preferences[PreferencesKeys.NUMBER_OF_BOTS] ?: 3,
                        victoryPoints = preferences[PreferencesKeys.VICTORY_POINTS] ?: 10,
                        timer = preferences[PreferencesKeys.TIMER] ?: 30_000L
                    )
                }
                .collect { loadedSettings ->
                    _settings.value = loadedSettings
                }
        }
    }

    fun updatePlayerName(name: String) {
        _settings.value = _settings.value?.copy(playerName = name)
        saveSettings()
    }

    fun updatePlayerColor(color: Color) {
        _settings.value = _settings.value?.copy(playerColor = color)
        saveSettings()
    }

    fun updateNumberOfBots(bots: Int) {
        _settings.value = _settings.value?.copy(numberOfBots = bots)
        saveSettings()
    }

    fun updateVictoryPoints(points: Int) {
        _settings.value = _settings.value?.copy(victoryPoints = points)
        saveSettings()
    }

    fun updateTimer(timer: Long) {
        _settings.value = _settings.value?.copy(timer = timer)
        saveSettings()
    }

    private fun saveSettings() {
        val current = _settings.value ?: return
        scope.launch {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.PLAYER_NAME] = current.playerName
                preferences[PreferencesKeys.PLAYER_COLOR] = current.playerColor.toArgb().toLong()
                preferences[PreferencesKeys.NUMBER_OF_BOTS] = current.numberOfBots
                preferences[PreferencesKeys.VICTORY_POINTS] = current.victoryPoints
                preferences[PreferencesKeys.TIMER] = current.timer
            }
        }
    }
}
