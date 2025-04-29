package eric.bitria.hexon.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    initialConfig: GameSettings,
    onStartGame: (GameSettings) -> Unit,
    onBack: () -> Unit
) {
    var config by remember { mutableStateOf(initialConfig) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "Game Settings",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = config.playerName,
            onValueChange = { config = config.copy(playerName = it) },
            label = { Text("Player Name") },
            modifier = Modifier.fillMaxWidth()
        )

        SettingsOptionGroup(
            title = "Number of Bots",
            options = listOf(1, 2, 3),
            selected = config.numberOfBots,
            onSelected = { config = config.copy(numberOfBots = it) }
        )

        SettingsOptionGroup(
            title = "Game Timer",
            options = listOf(30, 60, 90),
            selected = (config.timer / 1000).toInt(),
            optionFormatter = { "$it seconds" },
            onSelected = { config = config.copy(timer = it * 1000L) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.weight(1f)
            ) {
                Text("Back")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { onStartGame(config) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Start Game")
            }
        }
    }
}

@Composable
private fun <T> SettingsOptionGroup(
    title: String,
    options: List<T>,
    selected: T,
    optionFormatter: (T) -> String = { it.toString() },
    onSelected: (T) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (option == selected),
                        onClick = { onSelected(option) }
                    )
                    .padding(12.dp)
            ) {
                RadioButton(
                    selected = (option == selected),
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    optionFormatter(option),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}