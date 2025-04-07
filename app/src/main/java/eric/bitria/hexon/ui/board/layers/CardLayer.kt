package eric.bitria.hexon.ui.board.layers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.player.Player

@Composable
fun CardsLayer(player: Player) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        val containerSize = min(maxWidth, maxHeight)
        val baseWidth = containerSize / (Resource.entries.size - 1)
        val cardHeight = baseWidth * GOLDEN_RATIO

        ResourceCardsRow(
            resources = player.deck.resourceCards,
            baseWidth = baseWidth,
            cardHeight = cardHeight
        )
    }
}

@Composable
private fun ResourceCardsRow(
    resources: Map<Resource, Int>,
    baseWidth: Dp,
    cardHeight: Dp
) {
    Row {
        getAvailableResources(resources).forEach { resource ->
            ResourceCard(
                resource = resource,
                count = resources[resource] ?: 0,
                width = baseWidth,
                height = cardHeight
            )
        }
    }
}

@Composable
private fun ResourceCard(
    resource: Resource,
    count: Int,
    width: Dp,
    height: Dp
) {
    val borderColor = calculateBorderColor(resource.color)

    Card(
        modifier = Modifier
            .clickable { /* Nothing */ }
            .size(width = width, height = height)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = resource.color,
            contentColor = Color.White
        ),
        border = BorderStroke(2.dp, borderColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        ResourceCardContent(resource, count, borderColor)
    }
}

@Composable
private fun ResourceCardContent(
    resource: Resource,
    count: Int,
    borderColor: Color
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ResourceNameText(resource.name, borderColor)
        ResourceCountText(count)
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
private fun ResourceNameText(
    resourceName: String,
    borderColor: Color
) {
    Text(
        text = resourceName.uppercase(),
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        modifier = Modifier
            .padding(top = 4.dp)
            .background(
                color = borderColor.copy(alpha = 0.3f),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 4.dp, vertical = 2.dp)
    )
}

@Composable
private fun ResourceCountText(count: Int) {
    Text(
        text = "$count",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

private const val GOLDEN_RATIO = 1.618f

private fun getAvailableResources(resources: Map<Resource, Int>) =
    Resource.entries
        .filter { resources.getOrDefault(it, 0) > 0 }
        .sortedBy { it.ordinal }


private fun calculateBorderColor(baseColor: Color) = Color(
    red = (baseColor.red * 1.6f).coerceIn(0f, 1f),
    green = (baseColor.green * 1.6f).coerceIn(0f, 1f),
    blue = (baseColor.blue * 1.6f).coerceIn(0f, 1f),
    alpha = baseColor.alpha
)