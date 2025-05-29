package eric.bitria.hexon.view.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color

class LogManager() {
    private val _logs = mutableStateListOf<LogEntry>()

    val logs: SnapshotStateList<LogEntry> get() = _logs

    fun addLog(text: String, color: Color = Color.Unspecified) {
        _logs.add(LogEntry(text, color))
    }

    fun clearLogs() {
        _logs.clear()
    }
}

data class LogEntry(
    val text: String,
    val color: Color
)
