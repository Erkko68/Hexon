package eric.bitria.hexon.ui.utils.modifier

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

// Class to hold zoom state values
class ZoomState {
    var zoomLevel by mutableFloatStateOf(1f)
    var offsetX by mutableFloatStateOf(0f)
    var offsetY by mutableFloatStateOf(0f)
    var centerX by mutableFloatStateOf(0f)
    var centerY by mutableFloatStateOf(0f)
    var maxOffset by mutableFloatStateOf(0f)
}

fun Modifier.zoomable(zoomState: ZoomState, minZoom: Float = 1f, maxZoom: Float = 3f): Modifier {
    return this.pointerInput(Unit) {
        coroutineScope {
            launch {
                detectTapGestures(
                    onDoubleTap = {
                        // Animate the reset to initial state
                        launch {
                            val zoomAnim = Animatable(zoomState.zoomLevel)
                            val offsetXAnim = Animatable(zoomState.offsetX)
                            val offsetYAnim = Animatable(zoomState.offsetY)

                            coroutineScope {
                                // Animate zoom level
                                launch {
                                    zoomAnim.animateTo(
                                        targetValue = 1f,
                                        animationSpec = tween(300, easing = FastOutSlowInEasing)
                                    ) {
                                        zoomState.zoomLevel = value
                                    }
                                }

                                // Animate X offset
                                launch {
                                    offsetXAnim.animateTo(
                                        targetValue = zoomState.centerX,
                                        animationSpec = tween(300)
                                    ) {
                                        zoomState.offsetX = value
                                    }
                                }

                                // Animate Y offset
                                launch {
                                    offsetYAnim.animateTo(
                                        targetValue = zoomState.centerY,
                                        animationSpec = tween(300)
                                    ) {
                                        zoomState.offsetY = value
                                    }
                                }
                            }
                        }
                    }
                )
            }

            launch {
                detectTransformGestures { centroid, pan, zoom, _ ->
                    // Cancel any ongoing animations when new gesture occurs
                    coroutineContext.job.cancelChildren()

                    val newZoom = (zoomState.zoomLevel * zoom).coerceIn(minZoom, maxZoom)
                    val contentFocalX = (centroid.x - zoomState.offsetX) / zoomState.zoomLevel
                    val contentFocalY = (centroid.y - zoomState.offsetY) / zoomState.zoomLevel

                    val newOffsetX = centroid.x - contentFocalX * newZoom + pan.x
                    val newOffsetY = centroid.y - contentFocalY * newZoom + pan.y

                    val clampedOffsetX = (newOffsetX - zoomState.centerX).coerceIn(
                        -zoomState.maxOffset,
                        zoomState.maxOffset
                    ) + zoomState.centerX
                    val clampedOffsetY = (newOffsetY - zoomState.centerY).coerceIn(
                        -zoomState.maxOffset,
                        zoomState.maxOffset
                    ) + zoomState.centerY

                    zoomState.zoomLevel = newZoom
                    zoomState.offsetX = clampedOffsetX
                    zoomState.offsetY = clampedOffsetY
                }
            }
        }
    }
}