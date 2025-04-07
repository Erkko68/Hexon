package eric.bitria.hexon.src.data.game

import androidx.compose.ui.graphics.Color

enum class Resource(val color: Color) {
    BRICK(Color.Red),
    WOOD(Color(0xFF4CAF50)),
    SHEEP(Color.Green),
    WHEAT(Color.Yellow),
    ORE(Color.Gray),
    NONE(Color(0xFFF4D03F))
}