package eric.bitria.hexon.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.player.Player

@Composable
fun EndScreen(
    onExitToMenu: () -> Unit,
    player: Player,
    winner: Player
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.6f))
    ) {
        // Centered content box
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp,
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "Game Over!",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Result text
                Text(
                    text = "Player Wins!",
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Exit button
                Button(
                    onClick = onExitToMenu,
                ) {
                    Text("Exit to Menu")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EndScreenPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            // Simulate game screen content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            )

            // Show end screen overlay
            EndScreen(
                onExitToMenu = {},
                player = Player("Player", Color.Red),
                winner = Player("Winner", Color.Green)
            )
        }
    }
}