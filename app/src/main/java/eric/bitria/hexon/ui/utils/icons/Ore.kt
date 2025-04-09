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

val Icons.Ore: ImageVector
    get() {
        if (_ore != null) {
            return _ore!!
        }
        _ore = Builder(name = "Ore", defaultWidth = 90.0.dp, defaultHeight = 90.dp, viewportWidth
                = 90.0f, viewportHeight = 90.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(66.58f, 19.32f)
                lineToRelative(8.66f, 9.76f)
                lineToRelative(-3.18f, 2.82f)
                lineToRelative(-8.66f, -9.76f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(89.43f, 31.0f)
                lineTo(72.51f, 11.0f)
                horizontalLineTo(40.19f)
                lineTo(27.86f, 26.0f)
                horizontalLineTo(14.91f)
                lineTo(0.35f, 43.0f)
                lineTo(14.91f, 60.0f)
                horizontalLineToRelative(27.53f)
                lineToRelative(7.71f, -9.0f)
                horizontalLineToRelative(22.36f)
                lineTo(89.43f, 31.0f)
                close()
                moveTo(40.6f, 56.0f)
                horizontalLineTo(16.75f)
                lineToRelative(-6.36f, -7.42f)
                lineToRelative(12.34f, -14.59f)
                lineToRelative(-3.25f, -2.75f)
                lineTo(7.59f, 45.31f)
                lineTo(5.62f, 43.0f)
                lineTo(16.75f, 30.0f)
                horizontalLineToRelative(23.85f)
                lineToRelative(11.13f, 13.0f)
                lineTo(40.6f, 56.0f)
                close()
                moveTo(53.57f, 47.0f)
                lineTo(57.0f, 43.0f)
                lineTo(42.44f, 26.0f)
                horizontalLineToRelative(-9.4f)
                lineToRelative(9.04f, -11.0f)
                horizontalLineToRelative(28.58f)
                lineToRelative(13.53f, 16.0f)
                lineTo(70.66f, 47.0f)
                horizontalLineTo(53.57f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(76.29f, 59.0f)
                horizontalLineTo(56.78f)
                lineTo(46.15f, 71.0f)
                lineToRelative(10.63f, 12.0f)
                horizontalLineToRelative(19.51f)
                lineToRelative(10.63f, -12.0f)
                lineTo(76.29f, 59.0f)
                close()
                moveTo(74.49f, 79.0f)
                horizontalLineTo(58.58f)
                lineToRelative(-7.09f, -8.0f)
                lineToRelative(7.09f, -8.0f)
                horizontalLineTo(74.49f)
                lineToRelative(3.54f, 4.0f)
                horizontalLineTo(68.0f)
                verticalLineToRelative(4.0f)
                horizontalLineToRelative(13.57f)
                lineTo(74.49f, 79.0f)
                close()
            }
        }
        .build()
        return _ore!!
    }

private var _ore: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Icons.Ore, contentDescription = "")
    }
}
