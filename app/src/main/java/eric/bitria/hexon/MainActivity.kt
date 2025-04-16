package eric.bitria.hexon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eric.bitria.hexon.screen.GameScreen
import eric.bitria.hexon.screen.LaunchScreen
import eric.bitria.hexon.screen.Screen
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.theme.HexonTheme
import eric.bitria.hexon.view.GameViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HexonTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Screen.Launch.route) {
                    composable(Screen.Launch.route) {
                        LaunchScreen(
                            onStartGame = {
                                val player = Player(Color(0xFFFF5722))
                                navController.navigate(Screen.Game.route)
                                viewModel.startNewGame(
                                    players = listOf(player, Player(Color.Blue,true), Player(Color.Green,true)),
                                    player = player
                                )
                            }
                        )
                    }
                    composable(Screen.Game.route) {
                        GameScreen(
                            viewModel = viewModel,
                            onExitToMenu = { navController.popBackStack() }
                        )
                    }

                }
            }
        }
    }
}