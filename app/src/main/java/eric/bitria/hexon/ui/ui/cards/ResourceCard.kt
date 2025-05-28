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
            val maxTextSize = (maxHeight * 0.2f).coerceAtMost(80.dp).value.sp
            val iconSize = maxHeight * 0.5f

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
                            fontSize = maxTextSize,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    if (selected > 0) {
                        Box(modifier = Modifier.weight(1f)) {
                            Text(
                                textAlign = TextAlign.End,
                                text = "$selected",
                                color = Color.Red,
                                fontSize = maxTextSize,
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
