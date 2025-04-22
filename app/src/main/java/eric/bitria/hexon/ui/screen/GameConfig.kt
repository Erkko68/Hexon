package eric.bitria.hexon.ui.screen

data class GameConfig(
    val numberOfPlayers: Int = 4,
    val victoryPoints: Int = 10,
    val timer: Long = 30_000L,
)