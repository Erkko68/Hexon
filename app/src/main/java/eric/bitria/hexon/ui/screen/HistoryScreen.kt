package eric.bitria.hexon.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.persistent.database.GameResult
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.view.MainGameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(viewModel: MainGameViewModel) {
    val gameResults by viewModel.history.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Game History") })
        }
    ) { paddingValues ->
        if (gameResults.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No game results found.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(gameResults) { result ->
                    ExpandableGameResultItem(result)
                }
            }
        }
    }
}

@Composable
fun ExpandableGameResultItem(result: GameResult) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Summary Line
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Date: ${result.datePlayed}", style = MaterialTheme.typography.titleSmall)
                Text("Winner: ${result.winnerName}", style = MaterialTheme.typography.titleSmall)
                Text("You: ${result.playerName}", style = MaterialTheme.typography.titleSmall)
            }

            // Expandable detail
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    // --- Buildings ---
                    Text("Buildings", style = MaterialTheme.typography.titleMedium)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        result.buildingStats.forEach { (key, count) ->
                            val building = runCatching { Building.valueOf(key) }.getOrNull()
                            if (building != null && building != Building.NONE) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = building.icon,
                                        contentDescription = key,
                                        tint = building.color,
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Text("$count", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }

                    // --- Resources ---
                    Text("Resources", style = MaterialTheme.typography.titleMedium)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        result.resourceStats.forEach { (key, count) ->
                            val resource = runCatching { Resource.valueOf(key) }.getOrNull()
                            if (resource != null && resource != Resource.NONE) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = resource.icon,
                                        contentDescription = key,
                                        tint = resource.color,
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Text("$count", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

