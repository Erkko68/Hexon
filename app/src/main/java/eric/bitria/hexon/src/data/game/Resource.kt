package eric.bitria.hexon.src.data.game

import androidx.compose.ui.graphics.Color

enum class Resource(val color: Color) {
    BRICK(Color.Red),
    WOOD(Color.Green),
    SHEEP(Color(0xFF4CAF50)),
    WHEAT(Color.Yellow),
    ORE(Color.Gray),
    DESERT(Color(0xFFF4D03F))
}