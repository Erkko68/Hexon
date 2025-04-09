package eric.bitria.hexon.ui.utils.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Icons.Wheat: ImageVector
    get() {
        if (_wheat != null) {
            return _wheat!!
        }
        _wheat = Builder(name = "Wheat", defaultWidth = 90.0.dp, defaultHeight = 90.0.dp,
                viewportWidth = 90.0f, viewportHeight = 90.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(76.18f, 46.69f)
                lineToRelative(3.01f, 3.01f)
                lineToRelative(-7.61f, 7.61f)
                lineToRelative(-3.01f, -3.01f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(76.18f, 32.84f)
                lineToRelative(3.01f, 3.01f)
                lineToRelative(-7.61f, 7.61f)
                lineToRelative(-3.01f, -3.01f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(76.18f, 19.0f)
                lineToRelative(3.01f, 3.01f)
                lineToRelative(-7.61f, 7.61f)
                lineToRelative(-3.01f, -3.01f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(15.86f, 19.0f)
                lineToRelative(7.61f, 7.61f)
                lineToRelative(-3.01f, 3.01f)
                lineToRelative(-7.61f, -7.61f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(15.86f, 32.84f)
                lineToRelative(7.61f, 7.61f)
                lineToRelative(-3.01f, 3.01f)
                lineToRelative(-7.61f, -7.61f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(15.86f, 46.69f)
                lineToRelative(7.61f, 7.61f)
                lineToRelative(-3.01f, 3.01f)
                lineToRelative(-7.61f, -7.61f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(63.35f, 33.69f)
                lineToRelative(-3.46f, 0.05f)
                curveToRelative(3.21f, -4.13f, 3.39f, -9.28f, 3.4f, -9.91f)
                lineToRelative(0.06f, -4.37f)
                lineToRelative(-4.37f, 0.06f)
                curveToRelative(-0.48f, 0.01f, -3.57f, 0.11f, -6.83f, 1.56f)
                curveToRelative(0.57f, -1.5f, 0.97f, -3.2f, 0.97f, -5.05f)
                curveToRelative(0.0f, -6.01f, -4.24f, -10.54f, -4.72f, -11.04f)
                lineToRelative(-3.05f, -3.13f)
                lineToRelative(-3.05f, 3.13f)
                curveToRelative(-0.48f, 0.5f, -4.72f, 5.03f, -4.72f, 11.04f)
                curveToRelative(0.0f, 1.85f, 0.4f, 3.56f, 0.97f, 5.05f)
                curveToRelative(-3.27f, -1.45f, -6.35f, -1.55f, -6.83f, -1.56f)
                lineToRelative(-4.37f, -0.06f)
                lineToRelative(0.06f, 4.37f)
                curveToRelative(0.01f, 0.63f, 0.19f, 5.79f, 3.4f, 9.91f)
                lineToRelative(-3.46f, -0.05f)
                lineToRelative(0.06f, 4.37f)
                curveToRelative(0.01f, 0.63f, 0.19f, 5.79f, 3.4f, 9.91f)
                lineToRelative(-3.46f, -0.05f)
                lineToRelative(0.06f, 4.37f)
                curveToRelative(0.01f, 0.69f, 0.22f, 6.89f, 4.47f, 11.14f)
                curveTo(36.1f, 67.65f, 42.22f, 67.89f, 43.0f, 67.9f)
                verticalLineTo(84.0f)
                horizontalLineToRelative(4.0f)
                verticalLineTo(67.96f)
                verticalLineToRelative(0.0f)
                verticalLineToRelative(-0.06f)
                lineToRelative(0.48f, -0.0f)
                curveToRelative(0.69f, -0.01f, 6.99f, -0.22f, 11.24f, -4.47f)
                curveToRelative(4.25f, -4.25f, 4.51f, -10.45f, 4.52f, -11.14f)
                lineToRelative(0.09f, -4.37f)
                lineToRelative(-3.45f, 0.05f)
                curveToRelative(3.21f, -4.13f, 3.4f, -9.28f, 3.4f, -9.91f)
                lineTo(63.35f, 33.69f)
                close()
                moveTo(43.0f, 48.38f)
                verticalLineToRelative(1.04f)
                curveToRelative(-0.65f, -0.02f, -5.19f, -0.3f, -8.11f, -3.22f)
                curveToRelative(-3.15f, -3.15f, -3.22f, -8.19f, -3.22f, -8.19f)
                reflectiveCurveToRelative(5.04f, 0.07f, 8.19f, 3.22f)
                curveTo(42.13f, 43.49f, 42.8f, 46.74f, 43.0f, 48.38f)
                close()
                moveTo(47.62f, 49.41f)
                curveToRelative(0.0f, 0.0f, 0.07f, -5.04f, 3.22f, -8.19f)
                reflectiveCurveToRelative(8.19f, -3.22f, 8.19f, -3.22f)
                reflectiveCurveToRelative(-0.07f, 5.04f, -3.22f, 8.19f)
                curveTo(52.66f, 49.34f, 47.62f, 49.41f, 47.62f, 49.41f)
                close()
                moveTo(59.04f, 23.76f)
                curveToRelative(0.0f, 0.0f, -0.07f, 5.04f, -3.22f, 8.19f)
                curveToRelative(-3.15f, 3.15f, -8.19f, 3.22f, -8.19f, 3.22f)
                reflectiveCurveToRelative(0.07f, -5.04f, 3.22f, -8.19f)
                reflectiveCurveTo(59.04f, 23.76f, 59.04f, 23.76f)
                close()
                moveTo(45.35f, 7.94f)
                curveToRelative(0.0f, 0.0f, 3.52f, 3.61f, 3.52f, 8.07f)
                reflectiveCurveToRelative(-3.52f, 8.07f, -3.52f, 8.07f)
                reflectiveCurveToRelative(-3.52f, -3.61f, -3.52f, -8.07f)
                reflectiveCurveTo(45.35f, 7.94f, 45.35f, 7.94f)
                close()
                moveTo(39.86f, 26.98f)
                curveToRelative(2.81f, 2.81f, 3.17f, 7.12f, 3.21f, 8.02f)
                horizontalLineTo(43.0f)
                verticalLineToRelative(0.17f)
                curveToRelative(-0.65f, -0.02f, -5.19f, -0.3f, -8.11f, -3.22f)
                curveToRelative(-3.15f, -3.15f, -3.22f, -8.19f, -3.22f, -8.19f)
                reflectiveCurveTo(36.71f, 23.83f, 39.86f, 26.98f)
                close()
                moveTo(34.89f, 60.43f)
                curveToRelative(-3.15f, -3.15f, -3.22f, -8.19f, -3.22f, -8.19f)
                reflectiveCurveToRelative(5.04f, 0.07f, 8.19f, 3.22f)
                curveToRelative(2.27f, 2.27f, 2.94f, 5.51f, 3.14f, 7.15f)
                verticalLineToRelative(1.04f)
                curveTo(42.35f, 63.62f, 37.81f, 63.35f, 34.89f, 60.43f)
                close()
                moveTo(55.81f, 60.43f)
                curveToRelative(-3.15f, 3.15f, -8.19f, 3.22f, -8.19f, 3.22f)
                reflectiveCurveToRelative(0.07f, -5.04f, 3.22f, -8.19f)
                curveToRelative(3.15f, -3.15f, 8.19f, -3.22f, 8.19f, -3.22f)
                reflectiveCurveTo(58.97f, 57.28f, 55.81f, 60.43f)
                close()
            }
        }
        .build()
        return _wheat!!
    }

private var _wheat: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Icons.Wheat, contentDescription = "")
    }
}
