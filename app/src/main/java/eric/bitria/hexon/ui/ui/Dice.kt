package eric.bitria.hexon.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.ui.icons.dice._1
import eric.bitria.hexon.ui.icons.dice._2
import eric.bitria.hexon.ui.icons.dice._3
import eric.bitria.hexon.ui.icons.dice._4
import eric.bitria.hexon.ui.icons.dice._5
import eric.bitria.hexon.ui.icons.dice._6
import eric.bitria.hexon.ui.theme.HexonTheme

@Composable
fun DiceScreen(
    dice1: Int,
    dice2: Int,
    onRollClick: () -> Unit,
    enabled: Boolean = true
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            DiceIcon(value = dice1)
            DiceIcon(value = dice2)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if(enabled) {
            Button(onClick = onRollClick) {
                Text("Roll Dice")
            }
        }
    }
}

@Composable
private fun DiceIcon(value: Int) {
    val iconVector = when (value) {
        1 -> Icons._1
        2 -> Icons._2
        3 -> Icons._3
        4 -> Icons._4
        5 -> Icons._5
        6 -> Icons._6
        else -> Icons._1
    }

    Box(
        modifier = Modifier
            .size(64.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = iconVector,
            tint = Color.Black,
            contentDescription = "Dice showing $value",
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DicePreview() {
    HexonTheme {
        DiceScreen(
            dice1 = 3,
            dice2 = 5,
            onRollClick = {}
        )
    }
}