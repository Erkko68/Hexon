package eric.bitria.hexon.view.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TurnTimerManager(private val coroutineScope: CoroutineScope) {
    private var timerJob: Job? = null
    private var turnTimeMillis by mutableLongStateOf(30_000L) // Default, will be updated

    var timeLeftSeconds by mutableLongStateOf(0L)
        private set
    var totalTimeElapsed by mutableLongStateOf(0L) // Not used in original, but good for stats
        private set


    fun setTurnDuration(durationMillis: Long) {
        turnTimeMillis = durationMillis
        timeLeftSeconds = durationMillis / 1000
    }

    fun startTimer(onTimeExpired: () -> Unit) {
        stopTimer() // Cancel any existing timer
        timeLeftSeconds = turnTimeMillis / 1000
        timerJob = coroutineScope.launch {
            val startTime = System.currentTimeMillis()
            var elapsedSinceStart = 0L

            while (isActive) {
                val currentTime = System.currentTimeMillis()
                elapsedSinceStart = currentTime - startTime
                val remaining = turnTimeMillis - elapsedSinceStart

                if (remaining <= 0) {
                    timeLeftSeconds = 0
                    // totalTimeElapsed += turnTimeMillis // If you want to track total time spent actively in turns
                    onTimeExpired()
                    break
                } else {
                    timeLeftSeconds = remaining / 1000
                    // totalTimeElapsed += 1000 (or actual delay time) // More accurate to sum up actual elapsed per turn
                    delay(1000) // Check every second
                }
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun resetTotalTime() {
        totalTimeElapsed = 0L
    }
}