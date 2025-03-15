package eric.bitria.hexon.ui.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.ui.utils.modifier.ZoomState
import eric.bitria.hexon.ui.utils.modifier.zoomable

@Composable
fun BoardContainer(
    board: Board,
    modifier: Modifier = Modifier,
    content: @Composable (ZoomState) -> Unit
) {
    val zoomState = remember { ZoomState() }
    var containerSize by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { containerSize = it }
            .zoomable(zoomState)
    ) {
        LaunchedEffect(containerSize) {
            zoomState.offsetX = containerSize.width / 2f
            zoomState.offsetY = containerSize.height / 2f
        }
        content(zoomState)
    }
}