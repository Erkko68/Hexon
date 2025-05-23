package eric.bitria.hexon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eric.bitria.hexon.ui.screen.GameScreen
import eric.bitria.hexon.ui.screen.LaunchScreen
import eric.bitria.hexon.ui.screen.Screen
import eric.bitria.hexon.ui.screen.SettingsScreen
import eric.bitria.hexon.ui.theme.HexonTheme
import eric.bitria.hexon.view.MainGameViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainGameViewModel by viewModels()
    // No ho podrem fer al tenir la base de dades ja que tindrem una dependència, obligant a fer servir una factoria
    // O fes servir HILT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HexonTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Screen.Launch.route) {
                    composable(Screen.Launch.route) {
                        LaunchScreen(
                            onStartGame = {
                                navController.navigate(Screen.Settings.route)
                            }
                        )
                    }

                    composable(Screen.Settings.route) {
                        SettingsScreen(
                            viewModel = viewModel,
                            onStartGame = { config ->
                                viewModel.updateGameConfig(config)
                                viewModel.startNewGame()
                                navController.navigate(Screen.Game.route)
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(Screen.Game.route) {
                        GameScreen(
                            viewModel = viewModel,
                            onExitToMenu = {
                                navController.popBackStack()
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}