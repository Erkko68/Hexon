package eric.bitria.hexon.ui.screen

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.persistent.database.GameResult
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.view.MainGameViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun HistoryScreen(viewModel: MainGameViewModel) {
    val activity = LocalActivity.current ?: return
    val windowSizeClass = calculateWindowSizeClass(activity)
    val isTablet = windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium

    val gameResults by viewModel.history.collectAsState(emptyList())
    var selectedDateKey by rememberSaveable { mutableStateOf<String?>(null) }

    if (gameResults.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No game results found.", style = MaterialTheme.typography.bodyMedium)
        }
        return
    }

    Column(Modifier.fillMaxSize()) {

        Text(
            text = "Game History",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        if (isTablet) {
            Row(Modifier.fillMaxSize()) {
                GameResultList(
                    results = gameResults,
                    selectedDateKey = selectedDateKey,
                    onSelect = { selectedDateKey = it },
                    modifier = Modifier
                        .weight(1f)
                )
                Box(Modifier
                    .weight(1f)
                ) {
                    selectedDateKey?.let { key ->
                        gameResults.find { it.datePlayed == key }?.let {
                            GameResultDetail(it)
                        }
                    } ?: CenteredMessage("Select a game to see details")
                }
            }
        } else {
            LazyColumn(Modifier.fillMaxSize()) {
                items(gameResults) { result ->
                    val isSelected = result.datePlayed == selectedDateKey
                    Column {
                        GameResultListItem(
                            result = result,
                            isSelected = isSelected,
                            onClick = {
                                selectedDateKey = if (isSelected) null else result.datePlayed
                            }
                        )
                        if (isSelected) {
                            GameResultDetail(result)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GameResultList(
    results: List<GameResult>,
    selectedDateKey: String?,
    onSelect: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        items(results) { result ->
            GameResultListItem(
                result = result,
                isSelected = result.datePlayed == selectedDateKey,
                onClick = {
                    onSelect(if (selectedDateKey == result.datePlayed) null else result.datePlayed)
                }
            )
        }
    }
}

@Composable
private fun GameResultListItem(
    result: GameResult,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val background = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface

    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = if (isSelected) 4.dp else 1.dp,
        color = background,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .clickable { onClick() }
    ) {
        Row(
            Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Date: ${result.datePlayed}", style = MaterialTheme.typography.bodyLarge)
            Text("Winner: ${result.winnerName}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun GameResultDetail(result: GameResult) {
    var selectedPlayer by remember { mutableIntStateOf(-1) }

    Surface(
        shape = MaterialTheme.shapes.large,
        tonalElevation = 3.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            InfoRow("Date: ${result.datePlayed}", "Winner: ${result.winnerName}")

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                result.playerStats.forEachIndexed { index, player ->
                    val isPlayerSelected = selectedPlayer == index
                    val highlightModifier = if (isPlayerSelected) {
                        Modifier
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(6.dp)
                    } else Modifier

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
                                selectedPlayer = if (selectedPlayer == index) -1 else index
                            }
                            .then(highlightModifier)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = player.playerName,
                            tint = player.playerColor,
                            modifier = Modifier.size(36.dp)
                        )
                        Text(player.playerName, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .heightIn(max = 400.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                if (selectedPlayer != -1) {
                    val player = result.playerStats[selectedPlayer]
                    StatSection("Buildings", player.buildingStats) { Building.valueOf(it) }
                    StatSection("Resources", player.resourceStats) { Resource.valueOf(it) }
                } else {
                    CenteredMessage("Tap a player icon above to view their stats")
                }
            }
        }
    }
}

@Composable
private fun StatSection(
    title: String,
    stats: Map<String, Int>,
    resolve: (String) -> Enum<*>
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium
            )
            .padding(12.dp)
    ) {
        Text(title, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(bottom = 8.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            stats.forEach { (key, value) ->
                val entry = runCatching { resolve(key) }.getOrNull()
                val icon = when (entry) {
                    is Building -> entry.icon to entry.color
                    is Resource -> entry.icon to entry.color
                    else -> null
                }
                icon?.let { (iconImage, color) ->
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        tonalElevation = 1.dp,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Column(
                            Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(iconImage, contentDescription = key, tint = color, modifier = Modifier.size(32.dp))
                            Text("$value", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun InfoRow(left: String, right: String) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(left, style = MaterialTheme.typography.labelLarge)
        Text(right, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
private fun CenteredMessage(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(message, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
    }
}
