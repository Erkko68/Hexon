package eric.bitria.hexon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eric.bitria.hexon.ui.screen.GameScreen
import eric.bitria.hexon.ui.screen.HistoryScreen
import eric.bitria.hexon.ui.screen.LaunchScreen
import eric.bitria.hexon.ui.screen.Screen
import eric.bitria.hexon.ui.screen.SettingsScreen
import eric.bitria.hexon.ui.theme.HexonTheme
import eric.bitria.hexon.view.MainGameViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainGameViewModel by viewModels(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val application = requireNotNull(this@MainActivity).application
                    return MainGameViewModel(application) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel

        setContent {
            HexonTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Screen.Launch.route) {
                    composable(Screen.Launch.route) {
                        LaunchScreen(
                            onStartGame = {
                                viewModel.startNewGame()
                                navController.navigate(Screen.Game.route)
                            },
                            onOpenSettings = {
                                navController.navigate(Screen.Settings.route)
                            },
                            onOpenHistory = {
                                navController.navigate(Screen.History.route)
                            }
                        )
                    }

                    composable(Screen.Settings.route) {
                        SettingsScreen(
                            viewModel = viewModel,
                            onExitToMenu = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(Screen.Game.route) {
                        GameScreen(
                            viewModel = viewModel,
                            onExitToMenu = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(Screen.History.route) {
                        HistoryScreen(
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}