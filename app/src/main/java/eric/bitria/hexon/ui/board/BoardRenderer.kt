package eric.bitria.hexon.ui.board

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.ui.board.layers.AvailableEdgePositionsLayer
import eric.bitria.hexon.ui.board.layers.AvailableVertexPositionsLayer
import eric.bitria.hexon.ui.board.layers.EdgeLayer
import eric.bitria.hexon.ui.board.layers.HexagonalTileLayer
import eric.bitria.hexon.ui.board.layers.VertexLayer
import eric.bitria.hexon.view.GamePhase

@Composable
fun BoardRenderer(
    board: Board,
    edges: List<Edge>,
    vertices: List<Vertex>,
    onClick: (Any) -> Unit,
    phase: GamePhase
) {

    Box{
        // Base Hexagonal Tiles Layer
        HexagonalTileLayer(board)

        // Existing Buildings Layer
        VertexLayer(board.getVertices().filter { it.hasBuilding() })

        // Existing Roads Layer
        EdgeLayer(board.getEdges().filter { it.hasBuilding() })

        if(phase == GamePhase.PLACE_SETTLEMENT) {
            AvailableVertexPositionsLayer(
                vertices = vertices,
                onClick = { onClick(it) }
            )
        }

        if(phase == GamePhase.PLACE_ROAD) {
            AvailableEdgePositionsLayer(
                edges = edges,
                onClick = { onClick(it) }
            )
        }
    }
}