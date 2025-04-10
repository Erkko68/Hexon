package eric.bitria.hexon.src.data.game

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import eric.bitria.hexon.ui.icons.buildings.Castle
import eric.bitria.hexon.ui.icons.buildings.House
import eric.bitria.hexon.ui.icons.None
import eric.bitria.hexon.ui.icons.buildings.Trail

enum class Building(
    val recipe: Map<Resource, Int>,
    val value: Int,
    val color: Color,
    val icon: ImageVector
) {
    NONE(
        recipe = emptyMap(),
        value = 0,
        color = Color.Transparent,
        icon = Icons.None
    ),
    SETTLEMENT(
        recipe = mapOf(Resource.WOOD to 1, Resource.BRICK to 1, Resource.WHEAT to 1, Resource.SHEEP to 1),
        value = 1,
        color = Color(0xFF6D4C41),
        icon = Icons.House
    ),
    ROAD(
        recipe = mapOf(Resource.WOOD to 1, Resource.BRICK to 1),
        value = 0,
        color = Color(0xFF757575),
        icon = Icons.Trail
    ),
    CITY(
        recipe = mapOf(Resource.WHEAT to 2, Resource.ORE to 3),
        value = 2,
        color = Color(0xFF546E7A),
        icon = Icons.Castle
    )
}