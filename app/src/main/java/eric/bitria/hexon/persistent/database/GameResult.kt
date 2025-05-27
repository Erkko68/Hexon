package eric.bitria.hexon.persistent.database

import android.content.Context
import eric.bitria.hexon.R

data class GameResult(
    val datePlayed: String,
    val winnerName: String,
    val playerName: String,
    val buildingStats: Map<String, Int>,
    val resourceStats: Map<String, Int>
)

fun GameResult.toEmailString(context: Context): String {
    val sb = StringBuilder()
    sb.appendLine(context.getString(R.string.game_results))
    sb.appendLine(context.getString(R.string.played_on, datePlayed))
    sb.appendLine()
    sb.appendLine(context.getString(R.string.winner, winnerName))
    sb.appendLine()
    sb.appendLine(context.getString(R.string.your_stats))
    sb.appendLine()

    sb.appendLine(context.getString(R.string.buildings))
    buildingStats.forEach { (name, count) ->
        sb.appendLine("- $name: $count")
    }

    sb.appendLine()
    sb.appendLine(context.getString(R.string.resources))
    resourceStats.forEach { (name, count) ->
        sb.appendLine("- $name: $count")
    }

    sb.appendLine()
    sb.appendLine(context.getString(R.string.thanks_for_playing))
    return sb.toString()
}
