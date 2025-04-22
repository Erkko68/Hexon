package eric.bitria.hexon.ui.board.layers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.ui.board.LocalTileSize
import eric.bitria.hexon.ui.utils.geometry.HexConversions

@Composable
fun VertexLayer(
    vertices: Collection<Vertex>,
) {
    val tileSizePx = with(LocalDensity.current) { LocalTileSize.current.toPx() }
    val density = LocalDensity.current

    Box(modifier = Modifier.fillMaxSize()) {

        vertices.forEach { vertex ->
            vertex.player?.let {
                val position = HexConversions.getVertexPosition(vertex.getCoords(), tileSizePx)

                // Convert the position (Px) to Dp for Modifier.offset
                val offsetX = with(density) { position.x.toDp() }
                val offsetY = with(density) { position.y.toDp() }

                Icon(
                    imageVector = vertex.building.icon,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .offset(x = offsetX - ((tileSizePx * 0.35) / 2).dp, y = offsetY - ((tileSizePx * 0.355) / 2).dp)
                        .size((tileSizePx * 0.35).dp)
                )

                Icon(
                    imageVector = vertex.building.icon,
                    contentDescription = null,
                    tint = it.color,
                    modifier = Modifier
                        .offset(x = offsetX - ((tileSizePx * 0.3) / 2).dp, y = offsetY - ((tileSizePx * 0.3) / 2).dp)
                        .size((tileSizePx * 0.3).dp)
                )

            }
        }
    }
}


/**
 *                 Box(Modifier.fillMaxSize()) {
 *                     Canvas(
 *                         modifier = Modifier
 *                             .offset(
 *                                 x = offsetX - (iconSize / 2),
 *                                 y = offsetY - (iconSize / 2)
 *                             )
 *                             .size(iconSize)
 *                     ) {
 *                         drawHouse(center = center, size = iconSize.toPx() , color = player.color)
 *                     }
 *                 }
 */