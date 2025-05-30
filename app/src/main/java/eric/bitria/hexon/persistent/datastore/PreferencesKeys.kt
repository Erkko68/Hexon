package eric.bitria.hexon.persistent.datastore

import androidx.datastore.preferences.core.*

object PreferencesKeys {
    val PLAYER_NAME = stringPreferencesKey("player_name")
    val PLAYER_COLOR = longPreferencesKey("player_color")
    val PLAYER_EMAIL = stringPreferencesKey("player_email")
    val NUMBER_OF_BOTS = intPreferencesKey("number_of_bots")
    val VICTORY_POINTS = intPreferencesKey("victory_points")
    val TIMER = longPreferencesKey("timer")
}