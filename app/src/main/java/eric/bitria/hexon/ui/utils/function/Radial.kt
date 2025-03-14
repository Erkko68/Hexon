package eric.bitria.hexon.ui.utils.function

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlin.math.sqrt

/**
 * Converts axial coordinates (q, r) to screen coordinates (x, y).
 *
 * @param q Axial coordinate q
 * @param r Axial coordinate r
 * @param tileSize Size of the tile (hexagon radius)
 * @return Pair of screen coordinates (x, y)
 */
@Composable
fun axialToScreen(
    q: Int,
    r: Int,
    tileSize: Dp
): Pair<Float, Float> {
    val tileSizePx = with(LocalDensity.current) { tileSize.toPx() }

    // Convert axial coordinates to screen coordinates for a pointy-oriented hexagon
    val x = tileSizePx * (sqrt(3f) * (q + r / 2f))
    val y = tileSizePx * (3f / 2 * r)

    return Pair(x, y)
}