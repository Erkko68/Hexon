package eric.bitria.hexon.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.R
import eric.bitria.hexon.view.MainGameViewModel

@Composable
fun SettingsScreen(
    viewModel: MainGameViewModel,
    onStartGame: (GameSettings) -> Unit,
    onBack: () -> Unit
) {
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
            value = viewModel.gameSettings.playerName,
            onValueChange = { (viewModel::updatePlayerName)(it) },
            label = { Text(stringResource(R.string.player_name)) },
            modifier = Modifier.fillMaxWidth()
        )

        ColorPicker(
            selectedColor = viewModel.gameSettings.playerColor,
            onColorSelected = { (viewModel::updatePlayerColor)(it) }
        )


        SettingsOptionGroup(
            title = stringResource(R.string.number_of_bots),
            options = listOf(1, 2, 3),
            selected = viewModel.gameSettings.numberOfBots,
            onSelected = { (viewModel::updateNumberOfBots)(it) }
        )

        SettingsOptionGroup(
            title = stringResource(R.string.game_timer_seconds),
            options = listOf(30, 60, 90),
            selected = (viewModel.gameSettings.timer / 1000).toInt(),
            optionFormatter = { "$it" },
            onSelected = { (viewModel::updateTimer)(it * 1000L) }
        )

        SettingsOptionGroup(
            title = stringResource(R.string.victory_points),
            options = listOf(5, 8, 10, 12),
            selected = (viewModel.gameSettings.victoryPoints).toInt(),
            optionFormatter = { "$it" },
            onSelected = { (viewModel::updateVictoryPoints)(it) }
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
                Text(stringResource(R.string.back))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { onStartGame(viewModel.gameSettings) },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.start_game))
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = (option == selected),
                            onClick = { onSelected(option) }
                        )
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = (option == selected),
                        onClick = null
                    )
                    Text(
                        optionFormatter(option),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ColorPicker(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    val colors = listOf(
        Color(0xFF8E44AD), // Deep Purple
        Color(0xFF27AE60), // Emerald
        Color(0xFFF39C12), // Vivid Orange
        Color(0xFF3498DB), // Lighter Blue
        Color(0xFF1ABC9C), // Aqua
    )


    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(stringResource(R.string.player_color), style = MaterialTheme.typography.titleMedium)

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(color, shape = CircleShape)
                        .border(
                            width = if (color == selectedColor) 3.dp else 1.dp,
                            color = if (color == selectedColor) Color.Black else Color.Gray,
                            shape = CircleShape
                        )
                        .clickable { onColorSelected(color) }
                )
            }
        }
    }
}
