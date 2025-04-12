package eric.bitria.hexon.ui.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T : Enum<T>> CardsContainer(
    cards: Array<T>,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    val cardSize = with(LocalCardSize.current) { LocalCardSize.current }

    Box(
        modifier = modifier
            .height(cardSize * 1.618f),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            cards.forEach { card ->
                Box(modifier = Modifier.width(cardSize)) {
                    content(card)
                }
            }
        }
    }
}
