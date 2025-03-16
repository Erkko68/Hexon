package eric.bitria.hexon.ui.board.layers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.utils.geometry.HexConversions
import kotlin.math.roundToInt

@Composable
fun AvailableVertexPositionsLayer(
    vertices: Collection<Vertex>,
    tileSize: Dp,
    player: Player
) {
    val tileSizePx = with(LocalDensity.current) { tileSize.toPx() }
    val circleRadius = tileSizePx * 0.2f

    Box(modifier = Modifier.fillMaxSize()) {
        vertices.forEach { vertex ->
            val position = HexConversions.getVertexPosition(vertex.getCoords(), tileSizePx)

            // Calculate clickable area size in pixels
            val clickableSizePx = circleRadius * 2.5f
            val clickableSize = with(LocalDensity.current) { clickableSizePx.toDp() }

            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            (position.x - clickableSizePx / 2).roundToInt(),
                            (position.y - clickableSizePx / 2).roundToInt()
                        )
                    }
                    .size(clickableSize)
                    .clickable {
                        vertex.placeBuilding(Building.CITY, player)
                    }
                    .drawBehind {
                        drawCircle(
                            color = Color.Red,
                            radius = circleRadius,
                            center = Offset(size.width / 2, size.height / 2)
                        )
                    }
            )
        }
    }
}