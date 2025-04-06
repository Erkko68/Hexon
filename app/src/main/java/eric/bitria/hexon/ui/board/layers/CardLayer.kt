package eric.bitria.hexon.ui.board.layers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.player.Player

@Composable
fun CardsLayer(player: Player) {
    val resources = player.deck.resourceCards

    // Filter out resources with a count of 0 and sort them according to the enum order
    val availableResources = Resource.entries
        .filter { resources.getOrDefault(it, 0) > 0 }
        .sortedBy { it.ordinal }

    // Render the cards in a row at the bottom left of the screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            availableResources.forEach { resource ->
                ResourceCard(resource, resources[resource] ?: 0)
            }
        }
    }
}

@Composable
fun ResourceCard(resource: Resource, count: Int) {
    val goldenRatio = 1.618f
    val baseWidth = 60.dp  // Shorter dimension (width)
    val cardHeight = baseWidth * goldenRatio  // Longer dimension (height)

    // Calculate border color with safe RGB clamping
    val baseColor = resource.color
    val borderColor = Color(
        red = (baseColor.red * 1.6f).coerceIn(0f, 1f),
        green = (baseColor.green * 1.6f).coerceIn(0f, 1f),
        blue = (baseColor.blue * 1.6f).coerceIn(0f, 1f),
        alpha = baseColor.alpha
    )

    Card(
        modifier = Modifier
            .size(width = baseWidth, height = cardHeight)
            .padding(2.dp),
        shape = RoundedCornerShape(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = resource.color,
            contentColor = Color.White
        ),
        border = BorderStroke(2.dp, borderColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Resource type label
            Text(
                text = resource.name.uppercase(),
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

            // Central count display
            Text(
                text = "$count",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Bottom spacer for balance
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}