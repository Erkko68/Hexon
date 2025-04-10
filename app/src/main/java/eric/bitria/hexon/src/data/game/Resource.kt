package eric.bitria.hexon.src.data.game

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import eric.bitria.hexon.ui.theme.HexonColors
import eric.bitria.hexon.ui.icons.resources.Brick
import eric.bitria.hexon.ui.icons.None
import eric.bitria.hexon.ui.icons.resources.Ore
import eric.bitria.hexon.ui.icons.resources.Sheep
import eric.bitria.hexon.ui.icons.resources.Wheat
import eric.bitria.hexon.ui.icons.resources.Wood

enum class Resource(
    val color: Color,
    val icon: ImageVector
) {
    BRICK(HexonColors.Brick, Icons.Brick),
    WOOD(HexonColors.Wood, Icons.Wood),
    SHEEP(HexonColors.Sheep, Icons.Sheep),
    WHEAT(HexonColors.Wheat, Icons.Wheat),
    ORE(HexonColors.Ore, Icons.Ore),
    NONE(HexonColors.None, Icons.None)
}