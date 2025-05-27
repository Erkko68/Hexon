package eric.bitria.hexon.persistent.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "game_results")
data class GameResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val datePlayed: String,
    val winnerName: String,
    val playerName: String,
    val buildingStatsJson: String,
    val resourceStatsJson: String
)

object GameResultConverters {
    @TypeConverter
    fun fromMap(map: Map<String, Int>): String = Gson().toJson(map)

    @TypeConverter
    fun toMap(json: String): Map<String, Int> {
        val type = object : TypeToken<Map<String, Int>>() {}.type
        return Gson().fromJson(json, type)
    }
}

fun GameResult.toEntity(): GameResultEntity {
    return GameResultEntity(
        datePlayed = datePlayed,
        winnerName = winnerName,
        playerName = playerName,
        buildingStatsJson = GameResultConverters.fromMap(buildingStats),
        resourceStatsJson = GameResultConverters.fromMap(resourceStats)
    )
}

fun GameResultEntity.toGameResult(): GameResult {
    return GameResult(
        datePlayed = datePlayed,
        winnerName = winnerName,
        playerName = playerName,
        buildingStats = GameResultConverters.toMap(buildingStatsJson),
        resourceStats = GameResultConverters.toMap(resourceStatsJson)
    )
}
