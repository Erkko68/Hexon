package eric.bitria.hexon.persistent.database

import androidx.compose.ui.graphics.Color

data class GameResult(
    val datePlayed: String,
    val winnerName: String,
    val playerStats: List<PlayerStats>
)

data class PlayerStats(
    val playerName: String,
    val playerColor: Color,
    val buildingStats: Map<String, Int>,
    val resourceStats: Map<String, Int>
)