package eric.bitria.hexon.ui.board

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.board.Board
import kotlin.math.min

// Constants for zoom limits
private const val MIN_ZOOM = 0.8f
private const val MAX_ZOOM = 2f

val LocalTileSize = staticCompositionLocalOf<Dp> { error("Tile size not provided") }

@Composable
fun ZoomContainer(
    board: Board,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    var containerSize by remember { mutableStateOf(Size.Zero) }
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    // Calculate the tile size dynamically based on scale and container dimensions
    val tileDp = with(density) {
        if (containerSize == Size.Zero) 0.dp
        else {
            val minDim = min(containerSize.width, containerSize.height)
            (minDim / density.density).dp / ((board.radius + 1) * 4) * scale
        }
    }

    // Center content when the container size changes
    LaunchedEffect(containerSize) {
        if (containerSize != Size.Zero) {
            offset = Offset(containerSize.width / 2, containerSize.height / 2)
        }
    }

    CompositionLocalProvider(LocalTileSize provides tileDp) {
        Box(
            Modifier
                .fillMaxSize()
                .onSizeChanged { size ->
                    containerSize = Size(size.width.toFloat(), size.height.toFloat())
                }
                .pointerInput(containerSize, board.radius) {
                    detectTransformGestures { centroid, pan, zoom, _ ->

                        // Clamped scale calculation
                        val newScale = (scale * zoom).coerceIn(MIN_ZOOM, MAX_ZOOM)

                        // Maintain focal point under centroid while zooming
                        val zoomedOffset = (offset - centroid) * (newScale / scale) + centroid

                        // Apply panning to maintain the content's position
                        var newOffset = zoomedOffset + pan / newScale

                        // Calculate the max offset to avoid content going off-screen
                        val maxOffset = tileDp.toPx() * board.radius * 1.5f
                        val center = Offset(containerSize.width / 2, containerSize.height / 2)

                        // Clamp the offset within the allowed range
                        newOffset = Offset(
                            x = (newOffset.x - center.x).coerceIn(-maxOffset, maxOffset) + center.x,
                            y = (newOffset.y - center.y).coerceIn(-maxOffset, maxOffset) + center.y
                        )

                        // Update scale and offset state
                        scale = newScale
                        offset = newOffset
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures(onDoubleTap = {
                        // Reset scale and position on double tap
                        scale = 1f
                        offset = Offset(containerSize.width / 2, containerSize.height / 2)
                    })
                }
                .offset(
                    x = with(density) { offset.x.toDp() },
                    y = with(density) { offset.y.toDp() }
                )
        ) {
            content()
        }
    }
}
