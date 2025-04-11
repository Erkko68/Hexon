package eric.bitria.hexon.ui.ui.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.ui.utils.Color.calculateBorderColor
import eric.bitria.hexon.view.enums.GameActions

@Composable
fun ActionCard(
    action: GameActions,
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    val borderColor = calculateBorderColor(action.color)

    Card(
        onClick = onClick,
        modifier = Modifier
            .aspectRatio(1f) // Square aspect ratio
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = action.color,
            contentColor = Color.Black
        ),
        border = BorderStroke(2.dp, borderColor),
        elevation = CardDefaults.cardElevation(4.dp),
        enabled = enabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ){
            Icon(
                imageVector = action.icon,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
            )
        }
    }
}