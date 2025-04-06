package eric.bitria.hexon.ui.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.ui.utils.modifier.LocalTileSize
import eric.bitria.hexon.ui.utils.modifier.ZoomState
import eric.bitria.hexon.ui.utils.modifier.zoomable
import kotlin.math.sqrt

@Composable
fun ZoomContainer(
    board: Board,
    content: @Composable () -> Unit
) {
    val baseTileSize = 32.dp
    val zoomState = remember { ZoomState() }
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current

    // Calculate dynamic clamping bounds based on zoom
    val maxOffset = remember(board.radius, zoomState.zoomLevel) {
        with(density) {
            (baseTileSize * zoomState.zoomLevel).toPx() * board.radius
        }
    }

    // Center position calculation
    val (centerX, centerY) = remember(containerSize) {
        if (containerSize == IntSize.Zero) Pair(0f, 0f)
        else Pair(containerSize.width/2f, containerSize.height/2f)
    }

    // Apply clamping relative to center
    val clampedOffsetX = remember(zoomState.offsetX, centerX, maxOffset) {
        (zoomState.offsetX - centerX).coerceIn(-maxOffset, maxOffset) + centerX
    }

    val clampedOffsetY = remember(zoomState.offsetY, centerY, maxOffset) {
        (zoomState.offsetY - centerY).coerceIn(-maxOffset, maxOffset) + centerY
    }

    CompositionLocalProvider(LocalTileSize provides baseTileSize * zoomState.zoomLevel) {
        Box(
            Modifier
                .fillMaxSize()
                .onSizeChanged { containerSize = it }
                .zoomable(zoomState)
                .offset {
                    IntOffset(
                        clampedOffsetX.toInt(),
                        clampedOffsetY.toInt()
                    )
                }
        ) {
            // Initial centering
            LaunchedEffect(containerSize) {
                if (containerSize != IntSize.Zero) {
                    zoomState.offsetX = containerSize.width / 2f
                    zoomState.offsetY = containerSize.height / 2f
                }
            }

            content()
        }
    }
}