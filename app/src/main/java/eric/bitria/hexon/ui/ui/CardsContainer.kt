package eric.bitria.hexon.ui.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T : Enum<T>> CardsContainer(
    cards: Array<T>,
    content: @Composable (T) -> Unit
) {
    val cardSize = with(LocalCardSize.current) { LocalCardSize.current }
    var isCardsVisible by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardSize * 1.618f)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToggleButton(
                cardSize = cardSize,
                isVisible = isCardsVisible,
                onToggle = { isCardsVisible = !isCardsVisible }
            )

            AnimatedCardsRow(
                isVisible = isCardsVisible,
                cards = cards,
                content = content
            )
        }
    }
}

@Composable
private fun <T : Enum<T>> AnimatedCardsRow(
    isVisible: Boolean,
    cards: Array<T>,
    content: @Composable (T) -> Unit
) {
    val cardSize = LocalCardSize.current

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + expandHorizontally(expandFrom = Alignment.Start),
        exit = fadeOut() + shrinkHorizontally(shrinkTowards = Alignment.Start),
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            cards.forEach { card ->
                Box(
                    modifier = Modifier
                        .width(cardSize)
                ) {
                    content(card)
                }
            }
        }
    }
}

@Composable
private fun ToggleButton(
    cardSize: Dp,
    isVisible: Boolean,
    onToggle: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isVisible) 0f else 180f,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier
            .height(cardSize * 1.618f)
            .width(cardSize * 1.618f)
            .background(Color.Red)
            .clickable(onClick = onToggle)
            .rotate(rotation),
        contentAlignment = Alignment.Center
    ) {
        // Optional icon/indicator
        Text(
            text = if (isVisible) "◀" else "▶",
            color = Color.White,
            fontSize = 24.sp
        )
    }
}