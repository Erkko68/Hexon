package eric.bitria.hexon.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun HexonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Use dynamic color scheme if the API level permits and dynamicColor is enabled.
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme(
            primary = HexonColors.DarkPrimary,
            secondary = HexonColors.DarkSecondary,
            tertiary = HexonColors.Sand,
            background = HexonColors.DarkBackground,
            surface = HexonColors.DarkSurface,
            onPrimary = HexonColors.DarkOnPrimary,
            onBackground = Color.White
        )
        else -> lightColorScheme(
            primary = HexonColors.LightPrimary,
            secondary = HexonColors.LightSecondary,
            tertiary = HexonColors.Sand,
            background = HexonColors.LightBackground,
            surface = HexonColors.LightSurface,
            onPrimary = HexonColors.LightOnPrimary,
            onSecondary = Color.Black,
            onBackground = Color.Black
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography  // Make sure you have defined Typography in your project
    ) {
        // The Surface enforces your overall background color.
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorScheme.background
        ) {
            content()
        }
    }
}
