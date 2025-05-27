package eric.bitria.hexon.ui.screen

sealed class Screen(val route: String) {
    object Launch : Screen("launch")
    object Settings : Screen("settings")
    object Game : Screen("game")
    object History : Screen("history")
}