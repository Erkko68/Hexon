package eric.bitria.hexon.persistent.database

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import eric.bitria.hexon.R

fun GameResult.toEmailString(context: Context): String {
    val sb = StringBuilder()
    sb.appendLine(context.getString(R.string.game_results))
    sb.appendLine(context.getString(R.string.played_on, datePlayed))
    sb.appendLine()
    sb.appendLine(context.getString(R.string.winner, winnerName))
    sb.appendLine()

    playerStats.forEach { player ->
        sb.appendLine(context.getString(R.string.stats_for, player.playerName))
        sb.appendLine(context.getString(R.string.buildings))
        player.buildingStats.forEach { (name, count) ->
            sb.appendLine("- $name: $count")
        }
        sb.appendLine(context.getString(R.string.resources))
        player.resourceStats.forEach { (name, count) ->
            sb.appendLine("- $name: $count")
        }
        sb.appendLine()
    }

    sb.appendLine(context.getString(R.string.thanks_for_playing))
    return sb.toString()
}

fun GameResult.toEntity(): GameResultEntity {
    return GameResultEntity(
        datePlayed = datePlayed,
        winnerName = winnerName,
        playerStatsJson = GameResultConverters.fromPlayerStatsList(playerStats)
    )
}

fun GameResultEntity.toGameResult(): GameResult {
    return GameResult(
        datePlayed = datePlayed,
        winnerName = winnerName,
        playerStats = GameResultConverters.toPlayerStatsList(playerStatsJson)
    )
}


object GameResultConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromPlayerStatsList(list: List<PlayerStats>): String = gson.toJson(list)

    @TypeConverter
    fun toPlayerStatsList(json: String): List<PlayerStats> {
        val type = object : TypeToken<List<PlayerStats>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromColor(color: Color): Int = color.toArgb()

    @TypeConverter
    fun toColor(colorInt: Int): Color = Color(colorInt)

    private fun Color.toArgb(): Int {
        return android.graphics.Color.argb(
            (alpha * 255).toInt(),
            (red * 255).toInt(),
            (green * 255).toInt(),
            (blue * 255).toInt()
        )
    }

    private fun Color(argb: Int): Color {
        return Color(
            android.graphics.Color.red(argb) / 255f,
            android.graphics.Color.green(argb) / 255f,
            android.graphics.Color.blue(argb) / 255f,
            android.graphics.Color.alpha(argb) / 255f
        )
    }
}
