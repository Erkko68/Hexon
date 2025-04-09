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

val Icons.Castle: ImageVector
    get() {
        if (_castle != null) {
            return _castle!!
        }
        _castle = Builder(name = "Castle", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 483.01f, viewportHeight = 483.01f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(162.72f, 219.84f)
                horizontalLineTo(319.64f)
                curveToRelative(2.14f, 0.0f, 4.11f, -1.21f, 5.08f, -3.13f)
                curveToRelative(0.97f, -1.92f, 0.77f, -4.22f, -0.52f, -5.95f)
                lineToRelative(-77.55f, -104.71f)
                verticalLineTo(65.96f)
                lineToRelative(88.18f, -22.59f)
                curveToRelative(1.04f, -0.26f, 1.77f, -1.2f, 1.77f, -2.27f)
                curveToRelative(0.0f, -1.07f, -0.73f, -2.01f, -1.77f, -2.27f)
                lineTo(246.64f, 16.24f)
                verticalLineTo(5.48f)
                curveToRelative(0.0f, -3.03f, -2.46f, -5.48f, -5.49f, -5.48f)
                curveToRelative(-3.03f, 0.01f, -5.48f, 2.46f, -5.48f, 5.48f)
                lineToRelative(0.02f, 100.59f)
                lineToRelative(-77.53f, 104.68f)
                curveToRelative(-1.29f, 1.73f, -1.49f, 4.03f, -0.52f, 5.95f)
                curveTo(158.61f, 218.63f, 160.58f, 219.84f, 162.72f, 219.84f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(455.56f, 176.88f)
                curveToRelative(-4.06f, 0.0f, -7.34f, 3.29f, -7.34f, 7.34f)
                verticalLineToRelative(18.45f)
                horizontalLineTo(428.89f)
                verticalLineToRelative(-18.45f)
                curveToRelative(0.0f, -4.06f, -3.29f, -7.34f, -7.34f, -7.34f)
                horizontalLineToRelative(-17.8f)
                curveToRelative(-4.06f, 0.0f, -7.34f, 3.29f, -7.34f, 7.34f)
                verticalLineToRelative(18.45f)
                horizontalLineToRelative(-19.29f)
                verticalLineToRelative(-18.45f)
                curveToRelative(0.0f, -4.06f, -3.29f, -7.34f, -7.34f, -7.34f)
                horizontalLineToRelative(-17.82f)
                curveToRelative(-4.05f, 0.0f, -7.34f, 3.28f, -7.34f, 7.34f)
                lineToRelative(-0.19f, 184.57f)
                horizontalLineToRelative(-38.02f)
                verticalLineTo(242.13f)
                curveToRelative(0.0f, -3.44f, -2.78f, -6.21f, -6.2f, -6.21f)
                horizontalLineTo(182.18f)
                curveToRelative(-3.43f, 0.0f, -6.2f, 2.77f, -6.2f, 6.21f)
                verticalLineToRelative(126.65f)
                horizontalLineToRelative(-37.37f)
                lineToRelative(0.09f, -77.19f)
                curveToRelative(0.0f, -1.95f, -0.77f, -3.82f, -2.15f, -5.2f)
                curveToRelative(-1.38f, -1.38f, -3.25f, -2.15f, -5.2f, -2.15f)
                horizontalLineToRelative(-18.14f)
                curveToRelative(-4.06f, 0.0f, -7.34f, 3.29f, -7.34f, 7.34f)
                verticalLineToRelative(19.47f)
                horizontalLineTo(86.58f)
                verticalLineToRelative(-18.44f)
                curveToRelative(0.0f, -4.06f, -3.29f, -7.35f, -7.34f, -7.35f)
                horizontalLineTo(61.43f)
                curveToRelative(-4.05f, 0.0f, -7.34f, 3.29f, -7.34f, 7.35f)
                verticalLineToRelative(18.44f)
                horizontalLineTo(34.76f)
                verticalLineToRelative(-18.44f)
                curveToRelative(0.0f, -4.06f, -3.29f, -7.35f, -7.34f, -7.35f)
                horizontalLineTo(9.61f)
                curveToRelative(-4.06f, 0.0f, -7.34f, 3.28f, -7.36f, 7.33f)
                lineToRelative(-0.3f, 190.42f)
                horizontalLineToRelative(478.86f)
                lineToRelative(0.23f, -298.78f)
                curveToRelative(0.0f, -1.95f, -0.77f, -3.82f, -2.15f, -5.2f)
                curveToRelative(-1.38f, -1.38f, -3.25f, -2.15f, -5.2f, -2.15f)
                horizontalLineTo(455.56f)
                close()
                moveTo(92.1f, 395.63f)
                curveToRelative(0.0f, 3.44f, -2.77f, 6.21f, -6.2f, 6.21f)
                horizontalLineToRelative(-31.1f)
                curveToRelative(-3.43f, 0.0f, -6.2f, -2.78f, -6.2f, -6.21f)
                verticalLineToRelative(-27.74f)
                curveToRelative(0.0f, -12.01f, 9.73f, -21.75f, 21.75f, -21.75f)
                curveToRelative(12.01f, 0.0f, 21.75f, 9.74f, 21.75f, 21.75f)
                verticalLineTo(395.63f)
                close()
                moveTo(262.92f, 327.51f)
                curveToRelative(0.0f, 3.44f, -2.78f, 6.21f, -6.2f, 6.21f)
                horizontalLineToRelative(-31.1f)
                curveToRelative(-3.43f, 0.0f, -6.2f, -2.78f, -6.2f, -6.21f)
                verticalLineToRelative(-27.74f)
                curveToRelative(0.0f, -12.01f, 9.73f, -21.75f, 21.75f, -21.75f)
                curveToRelative(12.0f, 0.0f, 21.75f, 9.74f, 21.75f, 21.75f)
                verticalLineTo(327.51f)
                close()
                moveTo(434.42f, 287.25f)
                curveToRelative(0.0f, 3.44f, -2.77f, 6.21f, -6.2f, 6.21f)
                horizontalLineToRelative(-31.1f)
                curveToRelative(-3.43f, 0.0f, -6.2f, -2.78f, -6.2f, -6.21f)
                verticalLineToRelative(-27.74f)
                curveToRelative(0.0f, -12.01f, 9.73f, -21.75f, 21.75f, -21.75f)
                curveToRelative(12.01f, 0.0f, 21.75f, 9.74f, 21.75f, 21.75f)
                verticalLineTo(287.25f)
                close()
            }
        }
        .build()
        return _castle!!
    }

private var _castle: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Icons.Castle, contentDescription = "")
    }
}
