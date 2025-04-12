package eric.bitria.hexon.view.enums

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import eric.bitria.hexon.ui.icons.Cash
import eric.bitria.hexon.ui.icons.None

enum class GameActions(
    val color: Color,
    val icon: ImageVector,
    val type: Type,
){
    NONE(
        color = Color.Transparent,
        icon = Icons.None,
        type = Type.ROLL,
    ),

    END_TURN(
        color = Color.Red,
        icon = Icons.None,
        type = Type.ACTION,
    ),
    OPEN_TRADE(
        color = Color.Blue,
        icon = Icons.Cash,
        type = Type.ACTION,
    ),
    ACCEPT_TRADE(
        color = Color.Green,
        icon = Icons.Cash,
        type = Type.TRADE,
    ),
    CLOSE_TRADE(
        color = Color.Red,
        icon = Icons.Cash,
        type = Type.TRADE,
    ),
    ROLL_DICES(
        color = Color.Transparent,
        icon = Icons.None,
        type = Type.ROLL,
    );

    enum class Type{
        ACTION,
        TRADE,
        ROLL,
        NONE,
    }
}