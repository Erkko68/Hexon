package eric.bitria.hexon.persistent.datastore

import androidx.datastore.preferences.core.*

object GameSettingsKeys {
    val PLAYER_NAME = stringPreferencesKey("player_name")
    val PLAYER_COLOR = intPreferencesKey("player_color") // ARGB Int
    val NUMBER_OF_BOTS = intPreferencesKey("number_of_bots")
    val VICTORY_POINTS = intPreferencesKey("victory_points")
    val TIMER = longPreferencesKey("timer")
}
