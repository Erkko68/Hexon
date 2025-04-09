package eric.bitria.hexon.ui.theme

import androidx.compose.ui.graphics.Color

object HexonColors {

    val Blue = Color(0xFF588BAD)
    val DarkBlue = Color(0xFF1976D2)
    val Teal = Color(0xFF00796B)
    val Sand = Color(0xFFFFF8E1)

    val Brick = Color(0xFFB71C1C)
    val Wood = Color(0xFF2E7D32)
    val Sheep = Color(0xFF9CCC65)
    val Wheat = Color(0xFFFBC02D)
    val Ore = Color(0xFF616161)
    val None = Color(0xFFFFF9C4)

    // Additional colors for dynamic theming

    // Light theme colors: lighter, more vivid colors
    val LightPrimary = Color(0xFF90CAF9)
    val LightSecondary = Color(0xFF80CBC4)
    val LightBackground = Blue
    val LightSurface = Color(0xFFF5F5F5)
    val LightOnPrimary = Color.Black

    // Dark theme colors: deeper, moodier hues
    val DarkPrimary = DarkBlue
    val DarkSecondary = Teal
    val DarkBackground = Blue
    val DarkSurface = Color(0xFF1E1E1E)
    val DarkOnPrimary = Color.White
}
