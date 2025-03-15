package eric.bitria.hexon.ui.board.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.ui.utils.function.axialToScreen
import eric.bitria.hexon.ui.utils.modifier.ZoomState
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RenderAvailablePositions(
    board: Board,
    zoomState: ZoomState,
    tileSize: Dp
) {
    Box(
        modifier = Modifier
            .offset {
                IntOffset(zoomState.offsetX.toInt(), zoomState.offsetY.toInt())
            }
            .fillMaxSize()
    ) {
        board.getTiles().forEach { tile ->
            val (q, r) = tile.coords
            val (tileX, tileY) = axialToScreen(q, r, tileSize)

            // Iterate through all directions to render vertices
            Direction.entries.forEach { direction ->
                val vertex = tile.vertices[direction]
                if (vertex != null && board.canPlaceBuilding(vertex)) {
                    // Calculate the vertex position relative to the tile's center
                    val (dx, dy) = getVertexOffset(direction, tileSize)
                    val vertexX = tileX + dx
                    val vertexY = tileY + dy

                    // Render a circle at the vertex position
                    Box(
                        modifier = Modifier
                            .offset { IntOffset(vertexX.toInt(), vertexY.toInt()) }
                            .size(6.dp)
                            .drawBehind {
                                drawCircle(color = Color.Blue, radius = size.minDimension / 2)
                            }
                    )
                }
            }
        }
    }
}

/**
 * Computes the screen offset for a vertex given its direction in a hexagon.
 */
@Composable
private fun getVertexOffset(direction: Direction, tileSize: Dp): Pair<Float, Float> {
    val tileSizePx = with(LocalDensity.current) { tileSize.toPx() }
    val angleOffset = Math.PI / 2 // Start at 90 degrees (top vertex)
    val angle = angleOffset - (Math.PI / 3 * direction.ordinal) // Subtract for clockwise order
    val dx = tileSizePx * cos(angle).toFloat()
    val dy = -tileSizePx * sin(angle).toFloat() // Negate dy to flip vertically
    return Pair(dx, dy)
}