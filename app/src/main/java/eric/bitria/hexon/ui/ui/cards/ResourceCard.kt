package eric.bitria.hexon.ui.ui.cards

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.ui.utils.Color.calculateBorderColor

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ResourceCard(
    resource: Resource,
    count: Int,
    selected: Int = 0,
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    val borderColor = calculateBorderColor(resource.color)

    Card(
        enabled = enabled,
        onClick = onClick,
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = resource.color,
            contentColor = Color.Black
        ),
        border = BorderStroke(2.dp, borderColor),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val minTextSize = 12.sp
            val maxTextSize = 36.sp

            val iconSizePx = maxHeight * 0.7f
            val iconSize = iconSizePx.coerceIn(24.dp, 120.dp) // clamp icon size too

            // The text has roughly 30% of maxHeight available (since icon is 70%)
            val rawTextSizePx = maxHeight * 0.25f
            val rawTextSizeDp = rawTextSizePx.coerceIn(16.dp, 48.dp)
            val textSize = rawTextSizeDp.value.coerceIn(minTextSize.value, maxTextSize.value).sp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        Text(
                            textAlign = TextAlign.Start,
                            text = "${count - selected}",
                            fontSize = textSize,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    if (selected > 0) {
                        Box(modifier = Modifier.weight(1f)) {
                            Text(
                                textAlign = TextAlign.End,
                                text = "$selected",
                                color = Color.Red,
                                fontSize = textSize,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = resource.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(iconSize)
                            .align(Alignment.Center),
                    )
                }
            }
        }
    }
}
