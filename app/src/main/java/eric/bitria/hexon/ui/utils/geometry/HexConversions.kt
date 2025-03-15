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

    fun getEdgeMidpoint(edgeCoords: Pair<AxialCoord, AxialCoord>, size: Float): Offset {
        val (a, b) = edgeCoords
        val start = axialToPixel(a.q, a.r, size)
        val end = axialToPixel(b.q, b.r, size)
        return Offset((start.x + end.x) / 2, (start.y + end.y) / 2)
    }
}