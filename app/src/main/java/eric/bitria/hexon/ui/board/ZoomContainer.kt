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
import kotlin.math.min

@Composable
fun ZoomContainer(
    board: Board,
    content: @Composable () -> Unit
) {
    val zoomState = remember { ZoomState() }
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current

    // Calculate dynamic base tile size based on container dimensions
    val baseTileSize = remember(containerSize, board.radius) {
        with(density) {
            if (containerSize == IntSize.Zero) return@remember 0.dp

            val minDimension = min(containerSize.width, containerSize.height)
            val tileSizePx = minDimension / ((board.radius + 1) * 4)
            tileSizePx.toDp()
        }
    }

    // Calculate scaled tile size with zoom
    val scaledTileSize = remember(baseTileSize, zoomState.zoomLevel) {
        baseTileSize * zoomState.zoomLevel
    }

    // Calculate center and maxOffset based on current scaled size and container
    val (centerX, centerY, maxOffset) = remember(containerSize, scaledTileSize, board.radius) {
        with(density) {
            val centerX = containerSize.width / 2f
            val centerY = containerSize.height / 2f
            val maxOffset = (scaledTileSize.toPx() * board.radius) * 1.5f
            Triple(centerX, centerY, maxOffset)
        }
    }

    // Update ZoomState with current center and maxOffset
    LaunchedEffect(centerX, centerY, maxOffset) {
        zoomState.centerX = centerX
        zoomState.centerY = centerY
        zoomState.maxOffset = maxOffset
    }

    CompositionLocalProvider(LocalTileSize provides scaledTileSize) {
        Box(
            Modifier
                .fillMaxSize()
                .onSizeChanged { containerSize = it }
                .zoomable(zoomState)
                .offset {
                    IntOffset(
                        zoomState.offsetX.toInt(),
                        zoomState.offsetY.toInt()
                    )
                }
        ) {
            // Initial centering when container size changes
            LaunchedEffect(containerSize) {
                if (containerSize != IntSize.Zero) {
                    zoomState.offsetX = zoomState.centerX
                    zoomState.offsetY = zoomState.centerY
                }
            }

            content()
        }
    }
}