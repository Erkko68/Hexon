package eric.bitria.hexon.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.R
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.ui.utils.geometry.HexConversions.axialToPixel
import eric.bitria.hexon.ui.utils.geometry.drawHexagon

@Composable
fun LaunchScreen(onStartGame: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.welcome_to_hexon),
            style = MaterialTheme.typography.headlineLarge.copy(
                shadow = Shadow(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    offset = Offset(4f, 4f),
                    blurRadius = 12f
                )
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Canvas(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .padding(24.dp)
        ) {
            val hexSize = minOf(size.width, size.height) / 8
            val center = Offset(size.width / 2, size.height / 2)

            listOf(
                AxialCoord(1, 0), AxialCoord(1, -1), AxialCoord(0, -1),
                AxialCoord(-1, 0), AxialCoord(-1, 1), AxialCoord(0, 1)
            ).sortedWith(compareBy({ it.q + it.r }, { it.r }))
                .forEach { coord ->
                val offset = axialToPixel(coord.q, coord.r, hexSize)
                drawHexagon(
                    center = center + offset,
                    size = hexSize,
                    color = Color(0xFF1DE9B6)
                )
            }
        }

        ElevatedButton(
            onClick = onStartGame,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            modifier = Modifier.padding(vertical = 24.dp)
        ) {
            Text(
                text = stringResource(R.string.start_game),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
            )
        }
    }
}