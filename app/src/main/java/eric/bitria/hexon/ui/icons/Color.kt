package eric.bitria.hexon.ui.icons
import androidx.compose.ui.graphics.Color

object Color {

    fun calculateBorderColor(color: Color): Color {
        val originalBrightness = 0.2126f * color.red + 0.7152f * color.green + 0.0722f * color.blue

        return if (originalBrightness < 0.5f) {
            // Attempt to lighten the color
            val lightenedColor = Color(
                red = (color.red * 1.6f).coerceIn(0f, 1f),
                green = (color.green * 1.6f).coerceIn(0f, 1f),
                blue = (color.blue * 1.6f).coerceIn(0f, 1f),
                alpha = color.alpha
            )
            // Check if lightening actually increased brightness
            val newBrightness = 0.2126f * lightenedColor.red + 0.7152f * lightenedColor.green + 0.0722f * lightenedColor.blue
            if (newBrightness > originalBrightness) {
                lightenedColor
            } else {
                // Fallback to darkening if lightening didn't help
                Color(
                    red = (color.red * 0.6f).coerceIn(0f, 1f),
                    green = (color.green * 0.6f).coerceIn(0f, 1f),
                    blue = (color.blue * 0.6f).coerceIn(0f, 1f),
                    alpha = color.alpha
                )
            }
        } else {
            // Darken the color
            Color(
                red = (color.red * 0.6f).coerceIn(0f, 1f),
                green = (color.green * 0.6f).coerceIn(0f, 1f),
                blue = (color.blue * 0.6f).coerceIn(0f, 1f),
                alpha = color.alpha
            )
        }
    }
}