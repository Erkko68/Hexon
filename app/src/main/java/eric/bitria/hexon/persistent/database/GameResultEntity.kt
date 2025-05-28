package eric.bitria.hexon.persistent.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_results")
data class GameResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val datePlayed: String,
    val winnerName: String,
    val playerStatsJson: String // Store JSON here
)
