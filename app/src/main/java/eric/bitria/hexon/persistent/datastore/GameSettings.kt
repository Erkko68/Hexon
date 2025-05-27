package eric.bitria.hexon.persistent.datastore

import androidx.compose.ui.graphics.Color

data class GameSettings(
    val playerName: String = "Player",
    val playerColor: Color = Color.Companion.Transparent,
    val numberOfBots: Int = 3,
    val victoryPoints: Int = 10,
    val timer: Long = 30_000L,
)