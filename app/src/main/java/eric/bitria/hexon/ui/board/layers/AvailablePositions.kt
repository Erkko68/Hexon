package eric.bitria.hexon.ui.board.layers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.player.Player


@Composable
fun AvailablePositionsLayer(
    board: Board,
    tileSize: Dp,
    player: Player
) {
    val availableVertices by remember(board) {
        derivedStateOf { board.getVertices().filter { board.canPlaceBuilding(it,player) } }
    }

    val availableEdges by remember(board) {
        derivedStateOf { board.getEdges().filter { board.canPlaceRoad(it,player) } }
    }

    AvailableVertexPositionsLayer(
        vertices = availableVertices,
        tileSize = tileSize,
        player = player
    )

    AvailableEdgePositionsLayer(edges = availableEdges, tileSize = tileSize, player = player)
}