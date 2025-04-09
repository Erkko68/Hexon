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

val Icons.Wood: ImageVector
    get() {
        if (_wood != null) {
            return _wood!!
        }
        _wood = Builder(name = "Wood", defaultWidth = 90.0.dp, defaultHeight = 90.0.dp,
                viewportWidth = 90.0f, viewportHeight = 90.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(75.72f, 21.84f)
                curveToRelative(-0.77f, 0.0f, -1.53f, 0.08f, -2.28f, 0.2f)
                curveToRelative(0.38f, -1.3f, 0.58f, -2.66f, 0.58f, -4.06f)
                curveToRelative(0.0f, -7.91f, -6.44f, -14.35f, -14.35f, -14.35f)
                curveToRelative(-3.84f, 0.0f, -7.43f, 1.5f, -10.14f, 4.2f)
                lineTo(33.0f, 21.23f)
                lineToRelative(-11.07f, 9.38f)
                curveToRelative(-3.43f, 3.03f, -5.61f, 7.44f, -5.61f, 12.37f)
                curveToRelative(0.0f, 1.21f, 0.14f, 2.38f, 0.38f, 3.52f)
                lineTo(5.78f, 57.42f)
                curveToRelative(-0.05f, 0.05f, -0.09f, 0.09f, -0.14f, 0.14f)
                lineTo(5.48f, 57.72f)
                lineToRelative(0.01f, 0.01f)
                curveToRelative(-2.86f, 2.97f, -4.63f, 6.99f, -4.63f, 11.43f)
                curveToRelative(0.0f, 9.1f, 7.41f, 16.51f, 16.51f, 16.51f)
                curveToRelative(6.14f, 0.0f, 11.5f, -3.38f, 14.34f, -8.37f)
                curveToRelative(2.84f, 4.99f, 8.2f, 8.37f, 14.34f, 8.37f)
                curveToRelative(5.95f, 0.0f, 11.17f, -3.18f, 14.07f, -7.92f)
                lineToRelative(27.56f, -34.04f)
                lineToRelative(0.67f, -0.72f)
                curveToRelative(1.44f, -2.28f, 1.71f, -4.09f, 1.71f, -6.79f)
                curveTo(90.07f, 28.28f, 83.63f, 21.84f, 75.72f, 21.84f)
                close()
                moveTo(53.29f, 10.17f)
                curveToRelative(1.8f, -1.48f, 4.02f, -2.28f, 6.38f, -2.28f)
                curveToRelative(2.94f, 0.0f, 5.59f, 1.27f, 7.43f, 3.29f)
                lineTo(52.13f, 27.29f)
                lineToRelative(3.11f, 2.9f)
                lineTo(69.33f, 15.04f)
                curveToRelative(0.28f, 0.93f, 0.44f, 1.92f, 0.44f, 2.95f)
                curveToRelative(0.0f, 1.77f, -0.46f, 3.48f, -1.32f, 4.98f)
                lineToRelative(-1.1f, 1.27f)
                curveToRelative(-0.34f, 0.28f, -1.47f, 1.5f, -1.78f, 1.82f)
                lineTo(49.15f, 45.3f)
                curveToRelative(0.11f, -0.76f, 0.18f, -1.53f, 0.18f, -2.32f)
                curveToRelative(0.0f, -8.96f, -7.18f, -16.26f, -16.08f, -16.49f)
                lineTo(53.29f, 10.17f)
                close()
                moveTo(18.34f, 50.88f)
                curveToRelative(0.37f, 0.68f, 0.8f, 1.32f, 1.26f, 1.94f)
                curveToRelative(-0.73f, -0.1f, -1.47f, -0.17f, -2.23f, -0.17f)
                curveToRelative(-0.29f, 0.0f, -0.57f, 0.03f, -0.85f, 0.04f)
                lineTo(18.34f, 50.88f)
                close()
                moveTo(29.59f, 69.92f)
                curveToRelative(-0.4f, 6.4f, -5.72f, 11.49f, -12.22f, 11.49f)
                curveToRelative(-6.76f, 0.0f, -12.26f, -5.5f, -12.26f, -12.26f)
                curveToRelative(0.0f, -3.34f, 1.34f, -6.36f, 3.52f, -8.58f)
                lineToRelative(0.16f, -0.16f)
                curveToRelative(2.21f, -2.17f, 5.24f, -3.52f, 8.58f, -3.52f)
                curveToRelative(6.5f, 0.0f, 11.82f, 5.09f, 12.22f, 11.49f)
                curveToRelative(-0.01f, 0.25f, -0.04f, 0.5f, -0.04f, 0.76f)
                reflectiveCurveTo(29.58f, 69.67f, 29.59f, 69.92f)
                close()
                moveTo(31.72f, 61.02f)
                curveToRelative(-0.34f, -0.59f, -0.71f, -1.15f, -1.12f, -1.7f)
                curveToRelative(0.69f, 0.09f, 1.39f, 0.16f, 2.11f, 0.16f)
                curveTo(32.35f, 59.98f, 32.02f, 60.49f, 31.72f, 61.02f)
                close()
                moveTo(20.57f, 42.98f)
                curveToRelative(0.0f, -6.76f, 5.5f, -12.26f, 12.26f, -12.26f)
                reflectiveCurveToRelative(12.26f, 5.5f, 12.26f, 12.26f)
                reflectiveCurveToRelative(-5.5f, 12.26f, -12.26f, 12.26f)
                reflectiveCurveTo(20.57f, 49.74f, 20.57f, 42.98f)
                close()
                moveTo(56.67f, 75.26f)
                lineTo(55.54f, 76.66f)
                lineToRelative(0.12f, 0.1f)
                curveToRelative(-2.25f, 2.83f, -5.71f, 4.66f, -9.6f, 4.66f)
                curveToRelative(-6.5f, 0.0f, -11.82f, -5.09f, -12.22f, -11.49f)
                curveToRelative(0.01f, -0.25f, 0.04f, -0.5f, 0.04f, -0.76f)
                reflectiveCurveToRelative(-0.03f, -0.51f, -0.04f, -0.76f)
                curveToRelative(0.4f, -6.4f, 5.72f, -11.49f, 12.22f, -11.49f)
                curveToRelative(6.76f, 0.0f, 12.26f, 5.5f, 12.26f, 12.26f)
                curveTo(58.32f, 71.38f, 57.71f, 73.46f, 56.67f, 75.26f)
                close()
                moveTo(84.88f, 40.42f)
                lineToRelative(-22.37f, 27.63f)
                curveToRelative(-0.11f, -1.6f, -0.44f, -3.13f, -0.97f, -4.57f)
                lineTo(76.13f, 46.69f)
                lineToRelative(-3.21f, -2.79f)
                lineTo(59.4f, 59.47f)
                curveToRelative(-2.59f, -3.55f, -6.56f, -6.03f, -11.11f, -6.65f)
                lineToRelative(21.23f, -24.58f)
                curveToRelative(0.08f, -0.06f, 0.16f, -0.12f, 0.25f, -0.18f)
                curveToRelative(0.0f, 0.0f, 0.28f, -0.17f, 0.39f, -0.28f)
                curveToRelative(1.64f, -1.09f, 3.57f, -1.68f, 5.58f, -1.68f)
                curveToRelative(5.57f, 0.0f, 10.1f, 4.53f, 10.1f, 10.1f)
                curveTo(85.82f, 37.66f, 85.49f, 39.1f, 84.88f, 40.42f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(17.0f, 73.82f)
                curveToRelative(-2.0f, 0.0f, -4.29f, -1.82f, -4.29f, -4.82f)
                horizontalLineTo(8.46f)
                curveToRelative(0.0f, 5.0f, 3.54f, 9.07f, 8.54f, 9.07f)
                verticalLineToRelative(-2.14f)
                curveToRelative(0.12f, 0.01f, 0.25f, 0.02f, 0.37f, 0.02f)
                lineTo(17.0f, 75.57f)
                verticalLineTo(73.82f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(46.0f, 73.82f)
                curveToRelative(-1.22f, 0.0f, -2.2f, -0.31f, -2.94f, -0.87f)
                lineToRelative(-0.7f, -0.7f)
                curveToRelative(-0.63f, -0.81f, -0.96f, -1.9f, -0.96f, -3.25f)
                horizontalLineToRelative(-4.25f)
                curveToRelative(0.0f, 5.0f, 3.85f, 9.07f, 8.85f, 9.07f)
                verticalLineToRelative(-2.13f)
                curveToRelative(0.02f, 0.0f, 0.04f, 0.0f, 0.06f, 0.0f)
                lineTo(46.0f, 75.89f)
                verticalLineTo(73.82f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(29.21f, 46.16f)
                curveToRelative(-0.67f, -0.77f, -1.05f, -1.82f, -1.05f, -3.16f)
                horizontalLineToRelative(-2.11f)
                lineTo(26.0f, 42.98f)
                curveToRelative(0.0f, 0.01f, 0.0f, 0.01f, 0.0f, 0.02f)
                horizontalLineToRelative(-2.09f)
                curveToRelative(0.0f, 5.0f, 4.09f, 8.9f, 9.09f, 8.9f)
                verticalLineToRelative(-4.25f)
                curveToRelative(-1.4f, 0.0f, -2.53f, -0.37f, -3.36f, -1.06f)
                lineTo(29.21f, 46.16f)
                close()
            }
        }
        .build()
        return _wood!!
    }

private var _wood: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Icons.Wood, contentDescription = "")
    }
}
