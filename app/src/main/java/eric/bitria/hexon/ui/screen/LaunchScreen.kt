package eric.bitria.hexon.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.ui.utils.geometry.HexConversions.axialToPixel
import eric.bitria.hexon.ui.utils.geometry.drawHexagon

@Composable
fun LaunchScreen(onStartGame: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Canvas simulating a fake board
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val hexSize = size.width / 6
                val centerX = size.width / 2
                val centerY = size.height / 2

                // Draw a central hex and a ring around it
                val directions = listOf(
                    AxialCoord(1, 0), AxialCoord(1, -1), AxialCoord(0, -1),
                    AxialCoord(-1, 0), AxialCoord(-1, 1), AxialCoord(0, 1)
                ).sortedWith(compareBy({ it.q + it.r }, { it.r }))

                directions.forEach { (dq, dr) ->
                    val offset = axialToPixel(dq, dr, hexSize)
                    drawHexagon(Offset(centerX + offset.x, centerY + offset.y), hexSize, Color(0xFF90CAF9))
                }
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Welcome to Hexon!", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onStartGame) {
                Text("Start Game")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                repeat(3) { index ->
                    Button(
                        onClick = { /* TODO: Add bot logic */ },
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text("Add Bot ${index + 1}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}