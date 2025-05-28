package eric.bitria.hexon.ui.ui.sections

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eric.bitria.hexon.src.player.Player

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun PlayersColumn(
    players: List<Player>,
    currentPlayer: Player,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()

    // Measure max width required for player names
    val maxTextWidth = remember(players) {
        players.maxOf {
            textMeasurer.measure(
                text = AnnotatedString(it.name),
                style = TextStyle(fontSize = 14.sp) // Base guess
            ).size.width
        }
    }.dp

    BoxWithConstraints(modifier = modifier) {
        val maxH = maxHeight

        // Max icon area available, subtracting spacing
        val spacing = 12.dp
        val totalSpacing = spacing * (players.size - 1).coerceAtLeast(0)

        val availableHeight = (maxH - totalSpacing)
        val idealIconSize = availableHeight / players.size

        // Clamp the icon size to stay within good-looking bounds
        val minIconSize = 32.dp
        val maxIconSize = 70.dp
        val baseIconSize = idealIconSize.coerceIn(minIconSize, maxIconSize)

        val currentPlayerSize = baseIconSize * 1.1f
        val otherPlayerSize = baseIconSize * 0.9f

        // Final width = icon size + padding + maxTextWidth
        val rowWidth = maxOf(currentPlayerSize, otherPlayerSize) + 12.dp + maxTextWidth

        fun textSize(iconSize: Dp): TextUnit = (iconSize.value * 0.35f).sp

        Column(
            verticalArrangement = Arrangement.spacedBy(spacing),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.width(rowWidth)
        ) {
            players.forEach { p ->
                val iconSize = if (p == currentPlayer) currentPlayerSize else otherPlayerSize

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(iconSize)
                            .clip(CircleShape)
                            .background(p.color),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Player Icon",
                            tint = Color.White,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Text(
                        text = p.name,
                        modifier = Modifier.padding(start = 12.dp),
                        color = if (p == currentPlayer) Color.Black else Color.DarkGray,
                        fontWeight = if (p == currentPlayer) FontWeight.Bold else FontWeight.Normal,
                        fontSize = textSize(iconSize)
                    )
                }
            }
        }
    }
}
