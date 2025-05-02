package eric.bitria.hexon.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.ui.utils.geometry.HexConversions.axialToPixel
import eric.bitria.hexon.ui.utils.geometry.drawHexagon

@Composable
fun LaunchScreen(
    onStartGame: () -> Unit,
    onOpenSettings: () -> Unit = {},
    onOpenHistory: () -> Unit = {}
) {
    var showHelp by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        bottomBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onOpenHistory) {
                    Icon(Icons.Default.DateRange, "History", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onOpenSettings) {
                    Icon(Icons.Default.Settings, "Settings", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { showHelp = true }) {
                    Icon(Icons.Default.Info, "Help", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .clickable { if (!showHelp) onStartGame() },
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f, fill = false),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    "HEXON",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Canvas section - main expanding area
            Box(
                modifier = Modifier
                    .weight(3f)
                    .padding(horizontal = 32.dp)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                DecorativeBoard(
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Bottom section - fixed height
            Column(
                modifier = Modifier.weight(1f, fill = false), // Take minimum needed space
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tap to Start",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    if (showHelp) {
        HelpScreen(onBackToMenu = { showHelp = false })
    }
}

@Composable
private fun DecorativeBoard(
    modifier: Modifier = Modifier,  // Add default modifier
    tileCountRadius: Int = 3,
) {
    val coords = buildList {
        for (q in -tileCountRadius..tileCountRadius) {
            val rMin = maxOf(-tileCountRadius, -q - tileCountRadius)
            val rMax = minOf(tileCountRadius, -q + tileCountRadius)
            for (r in rMin..rMax) add(AxialCoord(q, r))
        }
    }

    val tiles = coords.map { coord ->
        coord to Resource.entries.random()
    }

    val sortedTiles = tiles.sortedWith(
        compareBy(
            { it.first.q + it.first.r },
            { it.first.r }
        )
    )

    Canvas(modifier = modifier) {
        val totalHexes = ((tileCountRadius + 1) * 4)
        val hexSize = size.minDimension / totalHexes
        val center = Offset(size.width / 2, size.height / 2)

        sortedTiles.forEach { (coord, resource) ->
            val offset = axialToPixel(coord.q, coord.r, hexSize)
            drawHexagon(
                center = center + offset,
                size = hexSize,
                color = resource.color
            )
        }
    }
}