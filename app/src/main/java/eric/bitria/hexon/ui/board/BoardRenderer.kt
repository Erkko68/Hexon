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
import eric.bitria.hexon.view.utils.ClickHandler

@Composable
fun BoardRenderer(
    board: Board,
    edges: List<Edge>,
    vertices: List<Vertex>,
    clickHandler: ClickHandler
) {
    Box {
        // Base Hexagonal Tiles Layer
        HexagonalTileLayer(board.getTiles())

        // Existing Buildings Layer
        EdgeLayer(board.getEdges().filter { it.hasBuilding() })
        VertexLayer(board.getVertices().filter { it.hasBuilding() })

        AvailableVertexPositionsLayer(
            vertices = vertices,
            onClick = {
                if (clickHandler is ClickHandler.OnVertex) {
                    clickHandler.handler(it as Vertex)
                }
            }
        )

        AvailableEdgePositionsLayer(
            edges = edges,
            onClick = {
                if (clickHandler is ClickHandler.OnEdge) {
                    clickHandler.handler(it as Edge)
                }
            }
        )
    }
}
