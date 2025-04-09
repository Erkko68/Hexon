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

val Icons.Sheep: ImageVector
    get() {
        if (_sheep != null) {
            return _sheep!!
        }
        _sheep = Builder(name = "Sheep", defaultWidth = 90.0.dp, defaultHeight = 90.0.dp,
                viewportWidth = 90.0f, viewportHeight = 90.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(24.57f, 31.09f)
                curveToRelative(3.81f, 0.0f, 6.9f, -3.09f, 6.9f, -7.09f)
                horizontalLineToRelative(-4.25f)
                curveToRelative(0.0f, 2.0f, -1.17f, 2.84f, -2.63f, 2.84f)
                curveToRelative(-2.12f, 0.0f, -3.82f, -1.53f, -3.82f, -3.65f)
                curveToRelative(0.0f, -2.94f, 2.43f, -5.33f, 5.37f, -5.33f)
                curveToRelative(3.97f, 0.0f, 7.27f, 3.23f, 7.27f, 7.2f)
                curveToRelative(0.0f, 5.25f, -4.39f, 9.53f, -9.39f, 9.53f)
                verticalLineToRelative(4.25f)
                curveToRelative(7.0f, 0.0f, 13.64f, -6.21f, 13.64f, -13.8f)
                curveToRelative(0.0f, -6.31f, -5.27f, -11.47f, -11.59f, -11.47f)
                curveToRelative(-5.28f, 0.0f, -9.58f, 4.25f, -9.58f, 9.54f)
                curveTo(16.47f, 27.56f, 20.1f, 31.09f, 24.57f, 31.09f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(83.53f, 38.03f)
                lineToRelative(3.43f, -3.44f)
                lineToRelative(0.02f, -7.49f)
                lineToRelative(-2.65f, -2.65f)
                lineToRelative(0.02f, -0.02f)
                lineToRelative(-4.03f, -4.04f)
                lineToRelative(-7.5f, -0.02f)
                lineToRelative(-3.46f, 3.46f)
                lineToRelative(-0.18f, -0.0f)
                lineToRelative(-3.43f, -3.43f)
                lineToRelative(-7.5f, -0.02f)
                lineToRelative(-3.46f, 3.46f)
                lineToRelative(-0.18f, -0.0f)
                lineToRelative(-3.44f, -3.43f)
                lineToRelative(-7.5f, -0.02f)
                lineToRelative(-4.1f, 4.1f)
                lineToRelative(3.01f, 3.01f)
                lineToRelative(2.85f, -2.85f)
                lineToRelative(3.97f, 0.01f)
                lineToRelative(3.4f, 3.4f)
                lineToRelative(3.7f, 0.09f)
                lineToRelative(3.5f, -3.5f)
                lineToRelative(3.97f, 0.01f)
                lineToRelative(3.4f, 3.4f)
                lineToRelative(3.7f, 0.09f)
                lineToRelative(3.5f, -3.5f)
                lineToRelative(3.97f, 0.01f)
                lineToRelative(1.34f, 1.34f)
                lineToRelative(-0.02f, 0.02f)
                lineToRelative(2.85f, 2.85f)
                lineToRelative(-0.01f, 3.97f)
                lineToRelative(-3.4f, 3.4f)
                lineToRelative(-0.09f, 3.7f)
                lineToRelative(3.5f, 3.5f)
                lineToRelative(-0.01f, 7.72f)
                lineTo(76.71f, 55.0f)
                horizontalLineTo(42.65f)
                lineToRelative(-3.9f, -3.25f)
                lineToRelative(-2.72f, -5.72f)
                lineTo(23.14f, 44.3f)
                lineToRelative(-11.95f, 2.77f)
                curveToRelative(-1.61f, -1.47f, -3.08f, -4.43f, -3.92f, -6.66f)
                lineToRelative(8.33f, -9.15f)
                lineToRelative(-3.14f, -2.86f)
                lineTo(2.46f, 39.37f)
                lineToRelative(0.36f, 1.16f)
                curveToRelative(0.25f, 0.81f, 2.56f, 7.99f, 6.74f, 10.63f)
                lineToRelative(0.75f, 0.47f)
                lineToRelative(13.02f, -3.02f)
                lineToRelative(9.84f, 1.31f)
                lineToRelative(2.14f, 4.48f)
                lineTo(40.0f, 58.28f)
                verticalLineTo(70.0f)
                horizontalLineToRelative(-4.0f)
                verticalLineToRelative(4.0f)
                horizontalLineToRelative(8.0f)
                verticalLineTo(59.0f)
                horizontalLineToRelative(6.0f)
                verticalLineToRelative(14.0f)
                horizontalLineToRelative(-4.0f)
                verticalLineToRelative(4.0f)
                horizontalLineToRelative(8.0f)
                verticalLineTo(59.0f)
                horizontalLineToRelative(9.0f)
                verticalLineToRelative(11.0f)
                horizontalLineToRelative(-4.0f)
                verticalLineToRelative(4.0f)
                horizontalLineToRelative(8.0f)
                verticalLineTo(59.0f)
                horizontalLineToRelative(6.68f)
                lineTo(74.0f, 59.7f)
                verticalLineTo(73.0f)
                horizontalLineToRelative(-4.0f)
                verticalLineToRelative(4.0f)
                horizontalLineToRelative(8.0f)
                verticalLineTo(59.0f)
                lineToRelative(0.0f, 0.0f)
                verticalLineToRelative(-0.13f)
                lineToRelative(8.73f, -5.42f)
                lineToRelative(0.14f, -11.79f)
                lineToRelative(-3.4f, -3.46f)
                lineTo(83.53f, 38.03f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(74.07f, 39.57f)
                lineToRelative(3.01f, -3.01f)
                lineToRelative(-4.04f, -4.04f)
                lineToRelative(-7.5f, -0.02f)
                lineToRelative(-3.46f, 3.46f)
                lineToRelative(-0.18f, -0.01f)
                lineToRelative(-3.44f, -3.44f)
                lineToRelative(-7.49f, -0.02f)
                lineToRelative(-4.1f, 4.1f)
                lineToRelative(3.01f, 3.01f)
                lineToRelative(2.85f, -2.85f)
                lineToRelative(3.97f, 0.01f)
                lineToRelative(3.4f, 3.4f)
                lineToRelative(3.7f, 0.09f)
                lineToRelative(3.5f, -3.5f)
                lineToRelative(3.97f, 0.01f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(58.26f, 42.23f)
                lineToRelative(-4.1f, 4.1f)
                lineToRelative(3.01f, 3.01f)
                lineToRelative(2.85f, -2.85f)
                lineToRelative(3.97f, 0.01f)
                lineToRelative(2.79f, 2.79f)
                lineToRelative(3.01f, -3.01f)
                lineToRelative(-4.04f, -4.04f)
                close()
            }
        }
        .build()
        return _sheep!!
    }

private var _sheep: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Icons.Sheep, contentDescription = "")
    }
}
