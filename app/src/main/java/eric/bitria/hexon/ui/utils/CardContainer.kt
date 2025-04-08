package eric.bitria.hexon.ui.utils


import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

private const val GOLDEN_RATIO = 1.618f

@Composable
fun <T : Enum<T>> CardsContainer(
    cards: Array<T>,
    content: @Composable (T) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val containerHeight = (minOf(screenWidth, screenHeight) / 5) * GOLDEN_RATIO
    val cardWidth = (containerHeight - 32.dp) / GOLDEN_RATIO

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(containerHeight)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            cards.forEach { card ->
                Box(
                    modifier = Modifier
                        .width(cardWidth)
                        .fillMaxHeight()
                ) {
                    content(card)
                }
            }
        }
    }
}