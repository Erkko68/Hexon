package eric.bitria.hexon.screen

sealed class Screen(val route: String) {
    object Launch : Screen("launch")
    object Game : Screen("game")
}