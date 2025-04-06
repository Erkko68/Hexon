package eric.bitria.hexon.ui.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.board.layers.AvailableEdgePositionsLayer
import eric.bitria.hexon.ui.board.layers.AvailableVertexPositionsLayer
import eric.bitria.hexon.ui.board.layers.CardsLayer
import eric.bitria.hexon.ui.board.layers.EdgeLayer
import eric.bitria.hexon.ui.board.layers.HexagonalTileLayer
import eric.bitria.hexon.ui.board.layers.VertexLayer
import eric.bitria.hexon.ui.utils.modifier.ZoomState

@Composable
fun BoardRenderer(
    board: Board,
    player: Player
) {

    Box{
        // Base Hexagonal Tiles Layer
        HexagonalTileLayer(board)

        // Existing Buildings Layer
        VertexLayer(
            board.getVertices().filter { it.hasBuilding() },
            player
        )

        // Existing Roads Layer
        EdgeLayer(
            board.getEdges().filter { it.hasBuilding() },
            player
        )

        AvailableVertexPositionsLayer(
            vertices = board.getVertices().filter { board.canPlaceBuilding(it,player) },
            player = player
        )

        AvailableEdgePositionsLayer(
            edges = board.getEdges().filter { board.canPlaceRoad(it,player) },
            player = player
        )
    }
}