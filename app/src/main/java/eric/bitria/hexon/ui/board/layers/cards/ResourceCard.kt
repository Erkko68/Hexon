package eric.bitria.hexon.ui.board.layers.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun ResourceCard(
    resource: Resource,
    count: Int,
    onClick: () -> Unit = {}
) {
    val borderColor = Color(
        red = (resource.color.red * 1.6f).coerceIn(0f, 1f),
        green = (resource.color.green * 1.6f).coerceIn(0f, 1f),
        blue = (resource.color.blue * 1.6f).coerceIn(0f, 1f),
        alpha = resource.color.alpha
    )

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(8.dp),
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
            Text(
                text = resource.name.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            )
            Text(
                text = "$count",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}