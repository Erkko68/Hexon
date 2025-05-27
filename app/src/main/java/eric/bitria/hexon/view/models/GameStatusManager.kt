package eric.bitria.hexon.view.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import eric.bitria.hexon.view.enums.GamePhase

class GameStatusManager {
    var gamePhase by mutableStateOf(GamePhase.NONE)
        private set
    var dice1 by mutableIntStateOf(0)
        private set
    var dice2 by mutableIntStateOf(0)
        private set

    val dices: Pair<Int, Int> get() = Pair(dice1, dice2)

    fun setPhase(newPhase: GamePhase) {
        gamePhase = newPhase
    }

    fun updateDiceRoll(d1: Int, d2: Int) {
        dice1 = d1
        dice2 = d2
    }
}