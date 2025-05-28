package eric.bitria.hexon.ui.ui.sections

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.ui.icons.dice._1
import eric.bitria.hexon.ui.icons.dice._2
import eric.bitria.hexon.ui.icons.dice._3
import eric.bitria.hexon.ui.icons.dice._4
import eric.bitria.hexon.ui.icons.dice._5
import eric.bitria.hexon.ui.icons.dice._6

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun DiceSection(
    dices: Pair<Int, Int>,
    onRollClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.padding(16.dp)) {
        val maxW = maxWidth
        val maxH = maxHeight

        // Dice size responsive to available width
        val diceSize = (maxW * 0.2f).coerceIn(48.dp, 96.dp)
        val spacing = (maxH * 0.05f).coerceIn(8.dp, 24.dp)

        val iconVector1 = when (dices.first) {
            1 -> Icons._1
            2 -> Icons._2
            3 -> Icons._3
            4 -> Icons._4
            5 -> Icons._5
            6 -> Icons._6
            else -> Icons._1
        }

        val iconVector2 = when (dices.second) {
            1 -> Icons._1
            2 -> Icons._2
            3 -> Icons._3
            4 -> Icons._4
            5 -> Icons._5
            6 -> Icons._6
            else -> Icons._1
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Dice display
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                horizontalArrangement = Arrangement.Center
            ) {
                DiceIcon(iconVector = iconVector1, size = diceSize)
                DiceIcon(iconVector = iconVector2, size = diceSize)
            }

            Spacer(modifier = Modifier.height(spacing))

            if (enabled) {
                Button(onClick = onRollClick) {
                    Text("Roll Dice")
                }
            }
        }
    }
}

@Composable
private fun DiceIcon(iconVector: ImageVector, size: Dp) {
    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = iconVector,
            contentDescription = "Dice",
            tint = Color.Black,
            modifier = Modifier
                .fillMaxSize()
                .padding(size * 0.125f)
        )
    }
}
