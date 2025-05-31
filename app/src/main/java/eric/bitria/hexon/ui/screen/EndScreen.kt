package eric.bitria.hexon.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Share
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
import eric.bitria.hexon.persistent.database.GameResult

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun EndScreen(
    onExitToMenu: () -> Unit,
    onShareResults: () -> Unit,
    gameResult: GameResult
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
            BoxWithConstraints {
                val isLandscape = maxWidth > maxHeight

                if (isLandscape) {
                    // 🌄 Landscape Layout (Side-by-side)
                    Row(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                            .heightIn(min = 300.dp)
                    ) {
                        // Winner Info
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.wins, gameResult.winnerName),
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = stringResource(R.string.winner_color),
                                tint = gameResult.playerStats.firstOrNull {
                                    it.playerName == gameResult.winnerName
                                }?.playerColor ?: Color.Gray,
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(top = 8.dp)
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(onClick = onExitToMenu) {
                                    Text(stringResource(R.string.exit_to_menu_msg))
                                }
                                Button(onClick = onShareResults) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = stringResource(R.string.share_results_msg),
                                    )
                                }
                            }
                        }

                        // Stats and Actions
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            GameResultDetail(result = gameResult)
                        }
                    }
                } else {
                    // 📱 Portrait Layout (Single column)
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Winner Section
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = stringResource(R.string.wins, gameResult.winnerName),
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = stringResource(R.string.winner_color),
                                tint = gameResult.playerStats.firstOrNull {
                                    it.playerName == gameResult.winnerName
                                }?.playerColor ?: Color.Gray,
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(top = 8.dp)
                            )
                        }

                        GameResultDetail(result = gameResult)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = onExitToMenu,
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text(stringResource(R.string.exit_to_menu_msg))
                            }

                            Button(
                                onClick = onShareResults,
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = stringResource(R.string.share_results_msg),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}