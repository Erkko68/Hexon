package eric.bitria.hexon.ui.utils.geometry

import androidx.compose.ui.geometry.Offset
import eric.bitria.hexon.src.data.AxialCoord

object HexConversions {
    private const val SQRT3 = 1.7320508f

    fun axialToPixel(q: Int, r: Int, size: Float): Offset {
        val x = size * (SQRT3 * q + SQRT3 / 2 * r)
        val y = size * (3f / 2 * r)
        return Offset(x, y)
    }

    fun getVertexPosition(vertexCoords: Triple<AxialCoord, AxialCoord, AxialCoord>, size: Float): Offset {
        // Average the three coordinates to find vertex center
        val (a, b, c) = vertexCoords
        val center = listOf(a, b, c).fold(Offset.Zero) { acc, coord ->
            acc + axialToPixel(coord.q, coord.r, size)
        } / 3f
        return center
    }
}