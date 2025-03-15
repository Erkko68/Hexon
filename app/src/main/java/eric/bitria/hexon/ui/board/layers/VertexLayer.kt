package eric.bitria.hexon.ui.board.layers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.ui.utils.geometry.HexConversions

@Composable
fun VertexLayer(
    vertices: Collection<Vertex>,
    tileSize: Dp
) {
    val tileSizePx = with(LocalDensity.current) { tileSize.toPx() }

    Canvas(modifier = Modifier.fillMaxSize()) {
        vertices.forEach { vertex ->
            val position = HexConversions.getVertexPosition(vertex.getCoords(), tileSizePx)
            drawCircle(
                color = Color.Blue,
                radius = tileSizePx * 0.2f,
                center = position
            )
        }
    }
}