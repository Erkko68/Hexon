package eric.bitria.hexon.ui.screen

sealed class Screen(val route: String) {
    object Launch : Screen("launch")
    object Game : Screen("game")
}