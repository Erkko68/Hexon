package eric.bitria.hexon.src.data.game

import androidx.compose.ui.graphics.Color

enum class Building(val recipe: Map<Resource, Int>, val value: Int, val color: Color) {
    NONE(emptyMap(),0, Color.Transparent),
    ROAD(mapOf(Resource.WOOD to 1, Resource.BRICK to 1),0, Color.Gray),
    SETTLEMENT(mapOf(Resource.WOOD to 1, Resource.BRICK to 1, Resource.WHEAT to 1, Resource.SHEEP to 1),1, Color(0xFF964B00)),
    CITY(mapOf(Resource.WHEAT to 2, Resource.ORE to 3),2,Color.Blue)
}