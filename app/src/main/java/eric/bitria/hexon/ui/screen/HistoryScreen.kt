package eric.bitria.hexon.ui.screen

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.persistent.database.GameResult
import eric.bitria.hexon.view.MainGameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: MainGameViewModel
) {
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
            ) {
                Text(
                    text = "No game results found.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(gameResults) { result ->
                    GameResultItem(result)
                    Divider()
                }
            }
        }
    }
}

@Composable
fun GameResultItem(result: GameResult) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Date: ${result.datePlayed}", style = MaterialTheme.typography.titleSmall)
        Text(text = "Winner: ${result.winnerName}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Player: ${result.playerName}", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Buildings:", style = MaterialTheme.typography.bodySmall)
        result.buildingStats.forEach { (building, count) ->
            Text(text = "- $building: $count", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Resources:", style = MaterialTheme.typography.bodySmall)
        result.resourceStats.forEach { (resource, count) ->
            Text(text = "- $resource: $count", style = MaterialTheme.typography.bodySmall)
        }
    }
}
