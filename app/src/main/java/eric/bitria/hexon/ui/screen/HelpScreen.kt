package eric.bitria.hexon.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.R
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.ui.icons.actions.Cash
import eric.bitria.hexon.ui.icons.dice._1

@Composable
fun HelpScreen(
    onBackToMenu: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.6f))
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp,
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.help_instructions),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Initial Placement
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = "Place initial buildings",
                        tint = Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.take_turns_placing_your_initial_buildings_before_the_game_starts),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Dice Roll and Resource Collection
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons._1,
                        contentDescription = "Roll dice",
                        tint = Color(0xFF6A1B9A),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.on_your_turn_roll_the_dice_players_with_buildings_on_tiles_matching_the_roll_collect_resources),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Trade
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Cash,
                        contentDescription = "Trade resources",
                        tint = Color(0xFF8D6E63),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.trade_4_of_any_resource_for_1_of_your_choice_via_the_trade_icon),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Production & Victory Points
                Text(
                    text = "Building Production & Victory:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                // Settlement
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Building.SETTLEMENT.icon,
                        contentDescription = "Settlement",
                        tint = Building.SETTLEMENT.color,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.produces_1_resource_and_gives_1_victory_point),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                // City
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Building.CITY.icon,
                        contentDescription = "City",
                        tint = Building.CITY.color,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.produces_2_resources_and_gives_2_victory_points),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Recipes Section with resource icons
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = "Building Recipes:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    // Road
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(text = "Road", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(
                            imageVector = Resource.BRICK.icon,
                            contentDescription = "Brick",
                            tint = Resource.BRICK.color,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = " x1", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Resource.WOOD.icon,
                            contentDescription = "Wood",
                            tint = Resource.WOOD.color,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = " x1", style = MaterialTheme.typography.bodyLarge)
                    }
                    // Settlement Recipe
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(text = "Settlement", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.width(12.dp))
                        listOf(Resource.BRICK, Resource.WOOD, Resource.WHEAT, Resource.SHEEP).forEach { res ->
                            Icon(
                                imageVector = res.icon,
                                contentDescription = res.name,
                                tint = res.color,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(text = " x1", style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                    // City Recipe
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(text = "City", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(
                            imageVector = Resource.ORE.icon,
                            contentDescription = "Ore",
                            tint = Resource.ORE.color,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = " x3", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Resource.WHEAT.icon,
                            contentDescription = "Wheat",
                            tint = Resource.WHEAT.color,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = " x2", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = onBackToMenu) {
                    Text(stringResource(R.string.close))
                }
            }
        }
    }
}
