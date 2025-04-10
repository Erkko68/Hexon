package eric.bitria.hexon.ui.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.ui.utils.modifier.ZoomState
import eric.bitria.hexon.ui.utils.modifier.zoomable
import kotlin.math.roundToInt

val LocalTileSize = staticCompositionLocalOf<Dp> { error("Tile size not provided") }

@Composable
fun ZoomContainer(
    board: Board,
    content: @Composable () -> Unit
) {
    val zoomState = remember { ZoomState() }
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    val baseTileSize = with(density) {
        minOf(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp) / ((board.radius + 1) * 4)
    }

    val scaledTileSize = baseTileSize * zoomState.zoomLevel

    // Calculate center and max offset
    LaunchedEffect(configuration, scaledTileSize, board.radius) {
        with(density) {
            val (sw, sh) = configuration.run { screenWidthDp.dp.toPx() to screenHeightDp.dp.toPx() }
            val maxOffset = (scaledTileSize.toPx() * board.radius) * 1.5f

            zoomState.apply {
                centerX = sw / 2
                centerY = sh / 2
                this.maxOffset = maxOffset
            }
        }
    }

    // Initial centering
    LaunchedEffect(Unit) {
        with(density) {
            val (sw, sh) = configuration.run { screenWidthDp.dp.toPx() to screenHeightDp.dp.toPx() }
            zoomState.apply {
                offsetX = sw / 2
                offsetY = sh / 2
            }
        }
    }

    CompositionLocalProvider(LocalTileSize provides scaledTileSize) {
        Box(
            Modifier
                .fillMaxSize()
                .zoomable(zoomState)
                .offset { IntOffset(zoomState.offsetX.roundToInt(), zoomState.offsetY.roundToInt()) }
        ) {
            content()
        }
    }
}