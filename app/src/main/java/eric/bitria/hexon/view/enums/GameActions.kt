package eric.bitria.hexon.view.enums

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import eric.bitria.hexon.ui.icons.None
import eric.bitria.hexon.ui.icons.actions.Cash
import eric.bitria.hexon.ui.icons.actions.CashCancel
import eric.bitria.hexon.ui.icons.actions.CashOk
import eric.bitria.hexon.ui.icons.actions.Skip

enum class GameActions(
    val color: Color,
    val icon: ImageVector,
) {
    END_TURN(
        color = Color(0xFFD32F2F),
        icon = Icons.Skip,
    ),
    OPEN_TRADE(
        color = Color(0xFF8D6E63),
        icon = Icons.Cash,
    ),
    CLOSE_TRADE(
        color = Color(0xFF6D4C41),
        icon = Icons.CashCancel,
    ),
    ACCEPT_TRADE(
        color = Color(0xFF388E3C),
        icon = Icons.CashOk,
    )
}
