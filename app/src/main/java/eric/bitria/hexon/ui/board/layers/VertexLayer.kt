package eric.bitria.hexon.ui.board.layers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.utils.geometry.HexConversions
import eric.bitria.hexon.ui.utils.modifier.LocalTileSize

@Composable
fun VertexLayer(
    vertices: Collection<Vertex>,
    player: Player
) {
    val tileSizePx = with(LocalDensity.current) { LocalTileSize.current.toPx() }

    Canvas(modifier = Modifier.fillMaxSize()) {
        vertices.forEach { vertex ->
            val position = HexConversions.getVertexPosition(vertex.getCoords(), tileSizePx)
            drawCircle(
                color = player.color,
                radius = tileSizePx * 0.2f,
                center = position
            )
        }
    }
}