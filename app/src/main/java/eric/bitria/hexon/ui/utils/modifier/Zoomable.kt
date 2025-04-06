package eric.bitria.hexon.ui.utils.modifier

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp

val LocalTileSize = staticCompositionLocalOf<Dp> { error("No tile size provided") }

class ZoomState {
    var zoomLevel by mutableFloatStateOf(1f)
    var offsetX by mutableFloatStateOf(0f)
    var offsetY by mutableFloatStateOf(0f)
}

fun Modifier.zoomable(zoomState: ZoomState, minZoom: Float = 1f, maxZoom: Float = 3f): Modifier {
    return this.pointerInput(Unit) {
        detectTransformGestures { centroid, pan, zoom, _ ->
            val newZoom = (zoomState.zoomLevel * zoom).coerceIn(minZoom, maxZoom)
            val contentFocalX = (centroid.x - zoomState.offsetX) / zoomState.zoomLevel
            val contentFocalY = (centroid.y - zoomState.offsetY) / zoomState.zoomLevel

            zoomState.zoomLevel = newZoom
            zoomState.offsetX = centroid.x - contentFocalX * zoomState.zoomLevel + pan.x
            zoomState.offsetY = centroid.y - contentFocalY * zoomState.zoomLevel + pan.y
        }
    }
}