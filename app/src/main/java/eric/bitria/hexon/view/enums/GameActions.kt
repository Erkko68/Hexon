package eric.bitria.hexon.view.enums

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.ui.icons.Cash
import eric.bitria.hexon.ui.icons.None
import eric.bitria.hexon.ui.icons.Paper
import eric.bitria.hexon.ui.icons.buildings.Castle
import eric.bitria.hexon.ui.icons.buildings.House
import eric.bitria.hexon.ui.icons.buildings.Trail

enum class GameActions(
    val color: Color,
    val icon: ImageVector,
    val type: Type,
    val card: Building,
){
    IDLE(
        color = Color.Transparent,
        icon =Icons.None,
        type = Type.NONE,
        card = Building.NONE
    ),
    PLACE_CITY(
        color = Color(0xFF546E7A),
        icon = Icons.Castle,
        type = Type.BUILD,
        card = Building.CITY
    ),
    PLACE_SETTLEMENT(
        color = Color(0xFF6D4C41),
        icon = Icons.House,
        type = Type.BUILD,
        card = Building.SETTLEMENT
    ),
    PLACE_ROAD(
        color = Color(0xFF757575),
        icon = Icons.Trail,
        type = Type.BUILD,
        card = Building.ROAD
    ),
    TRADE(
        color = Color.Blue,
        icon = Icons.Cash,
        type = Type.ACTION,
        card = Building.NONE
    ),
    BUY_PROGRESS_CARD(
        color = Color.Green,
        icon = Icons.Paper,
        type = Type.ACTION,
        card = Building.NONE
    ),
    END_TURN(
        color = Color.Red,
        icon = Icons.None,
        type = Type.ACTION,
        card = Building.NONE
    );

    enum class Type{
        BUILD,
        ACTION,
        NONE
    }
}