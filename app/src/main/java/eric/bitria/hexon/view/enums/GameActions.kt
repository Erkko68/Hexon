package eric.bitria.hexon.view.enums

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import eric.bitria.hexon.ui.icons.Cash
import eric.bitria.hexon.ui.icons.None

enum class GameActions(
    val color: Color,
    val icon: ImageVector,
){
    END_TURN(
        color = Color.Red,
        icon = Icons.Cash,
    ),
    OPEN_CLOSE_TRADE(
        color = Color.Blue,
        icon = Icons.Cash,
    ),
    ACCEPT_TRADE(
        color = Color.Green,
        icon = Icons.Cash,
    ),
    ROLL_DICES(
        color = Color.Transparent,
        icon = Icons.None,
    )
}