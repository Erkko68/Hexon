package eric.bitria.hexon.view.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import java.time.Duration
import java.time.LocalDateTime

class LogManager {
    private val _logs = mutableStateListOf<LogEntry>()
    private var initTime: LocalDateTime = LocalDateTime.now()

    val logs: SnapshotStateList<LogEntry> get() = _logs

    fun addLog(text: String, color: Color = Color.Unspecified) {
        val now = LocalDateTime.now()
        val elapsed = Duration.between(initTime, now)
        val mm = elapsed.toMinutes()
        val ss = elapsed.seconds % 60
        val timeString = "[%02d:%02d]".format(mm, ss)
        _logs.add(LogEntry("$timeString $text", color))
    }

    fun clearLogs() {
        _logs.clear()
        initTime = LocalDateTime.now()
    }

    companion object {
        fun logsToString(logsList: List<LogEntry>): String {
            return logsList.joinToString(separator = "\n") { it.text }
        }
    }
}

data class LogEntry(
    val text: String,
    val color: Color
)
