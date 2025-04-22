package eric.bitria.hexon.ui.utils.geometry

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import kotlin.math.cos
import kotlin.math.sin


fun DrawScope.drawHexagon(center: Offset, size: Float, color: Color) {
    val points = List(6) { i ->
        val angle = Math.toRadians((60 * i - 30).toDouble())
        Offset(
            x = center.x + size * cos(angle).toFloat(),
            y = center.y + size * sin(angle).toFloat()
        )
    }

    // Extrusion parameters (adjust these to control 3D depth)
    val depthX = size * 0.15f
    val depthY = size * 0.20f

    // Define all faces
    val rightFace = Path().apply {
        moveTo(points[0].x, points[0].y)
        lineTo(points[1].x, points[1].y)
        lineTo(points[1].x + depthX, points[1].y + depthY)
        lineTo(points[0].x + depthX, points[0].y + depthY)
        close()
    }

    val lowerEastFace = Path().apply {
        moveTo(points[1].x, points[1].y)
        lineTo(points[1].x + depthX, points[1].y + depthY)
        lineTo(points[2].x + depthX, points[2].y + depthY)
        lineTo(points[2].x, points[2].y)
        close()
    }

    val lowerWestFace = Path().apply {
        moveTo(points[2].x, points[2].y)
        lineTo(points[2].x + depthX, points[2].y + depthY)
        lineTo(points[3].x + depthX, points[3].y + depthY)
        lineTo(points[3].x, points[3].y)
        close()
    }

    // Draw faces in back-to-front order
    drawPath(lowerWestFace, color = color.copy(alpha = 0.7f).darken(0.25f))
    drawPath(lowerEastFace, color = color.copy(alpha = 0.7f).darken(0.2f))
    drawPath(rightFace, color = color.copy(alpha = 0.7f).darken(0.15f)) // Lightest

    // Draw top face last
    val topPath = Path().apply {
        moveTo(points[0].x, points[0].y)
        for (i in 1 until points.size) {
            lineTo(points[i].x, points[i].y)
        }
        close()
    }
    drawPath(topPath, color = color)
}


fun DrawScope.drawHouse(center: Offset, size: Float, color: Color) {
    val houseWidth = size * 0.8f             // Narrower width
    val baseHeight = size * 0.6f             // Tall body
    val roofHeight = size * 0.3f             // Proportional roof
    val depthX = size * 0.25f                 // Strong extrusion
    val depthY = size * 0.2f

    // Base rectangle (front face)
    val frontBottomLeft = Offset(center.x - houseWidth / 2, center.y + baseHeight / 2)
    val frontTopLeft = Offset(frontBottomLeft.x, frontBottomLeft.y - baseHeight)
    val frontTopRight = Offset(frontTopLeft.x + houseWidth, frontTopLeft.y)
    val frontBottomRight = Offset(frontTopRight.x, frontBottomLeft.y)

    // Roof peak
    val roofPeak = Offset(center.x, frontTopLeft.y - roofHeight)

    // Extrusion points (3D effect)
    val backTopRight = frontTopRight + Offset(depthX, -depthY)
    val backBottomRight = frontBottomRight + Offset(depthX, -depthY)
    val backRoofPeak = roofPeak + Offset(depthX, -depthY)

    // Draw faces back-to-front
    val sideWall = Path().apply {
        moveTo(frontTopRight.x, frontTopRight.y)
        lineTo(backTopRight.x, backTopRight.y)
        lineTo(backBottomRight.x, backBottomRight.y)
        lineTo(frontBottomRight.x, frontBottomRight.y)
        close()
    }

    val roofSide = Path().apply {
        moveTo(frontTopRight.x, frontTopRight.y)
        lineTo(roofPeak.x, roofPeak.y)
        lineTo(backRoofPeak.x, backRoofPeak.y)
        lineTo(backTopRight.x, backTopRight.y)
        close()
    }

    val roofFront = Path().apply {
        moveTo(frontTopLeft.x, frontTopLeft.y)
        lineTo(frontTopRight.x, frontTopRight.y)
        lineTo(roofPeak.x, roofPeak.y)
        close()
    }

    val frontWall = Path().apply {
        moveTo(frontTopLeft.x, frontTopLeft.y)
        lineTo(frontTopRight.x, frontTopRight.y)
        lineTo(frontBottomRight.x, frontBottomRight.y)
        lineTo(frontBottomLeft.x, frontBottomLeft.y)
        close()
    }

    // Draw with shading
    drawPath(sideWall, color.darken(0.2f))
    drawPath(roofSide, color.darken(0.1f))
    drawPath(roofFront, color)
    drawPath(frontWall, color)
}


private fun Color.darken(factor: Float): Color {
    return Color(
        red = (red * (1 - factor)).coerceIn(0f, 1f),
        green = (green * (1 - factor)).coerceIn(0f, 1f),
        blue = (blue * (1 - factor)).coerceIn(0f, 1f),
        alpha = alpha
    )
}
