package eric.bitria.hexon.ui.ui.sections

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eric.bitria.hexon.persistent.datastore.GameSettings
import eric.bitria.hexon.src.player.Player

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun InfoSection(
    modifier: Modifier = Modifier,
    timeLeft: Long = 0,
    player: Player,
    settings: GameSettings
) {
    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val formattedTime = String.format("%d:%02d", minutes, seconds)

    BoxWithConstraints(modifier = modifier) {
        val maxW = maxWidth
        val maxH = maxHeight

        // Scale base text size with width
        val baseFontSize = (maxW.value * 0.045f)
        val clampedFontSize = baseFontSize.coerceIn(14F, 30F).sp

        // Responsive padding inside the box
        val horizontalPadding = (maxW * 0.05f).coerceIn(8.dp, 30.dp)
        val verticalPadding = (maxH * 0.05f).coerceIn(4.dp, 22.dp)

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = horizontalPadding, vertical = verticalPadding)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formattedTime,
                        color = Color.Black,
                        fontSize = clampedFontSize
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "🏆 ${player.getVictoryPoints()} / ${settings.victoryPoints}",
                        color = Color.Black,
                        fontSize = clampedFontSize
                    )
                }
            }
        }
    }
}
