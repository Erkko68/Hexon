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

val Icons.Brick: ImageVector
    get() {
        if (_brick != null) {
            return _brick!!
        }
        _brick = Builder(name = "Brick", defaultWidth = 90.0.dp, defaultHeight = 90.dp,
                viewportWidth = 90.0f, viewportHeight = 90f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(69.0f, 38.0f)
                verticalLineToRelative(-1.18f)
                verticalLineTo(20.0f)
                horizontalLineTo(30.04f)
                lineTo(21.0f, 27.0f)
                lineToRelative(0.0f, 0.0f)
                verticalLineToRelative(11.0f)
                horizontalLineTo(10.06f)
                lineToRelative(-8.79f, 5.83f)
                lineTo(1.0f, 44.0f)
                lineToRelative(0.0f, 0.0f)
                verticalLineToRelative(22.0f)
                horizontalLineToRelative(37.0f)
                horizontalLineToRelative(4.0f)
                horizontalLineToRelative(40.0f)
                verticalLineToRelative(-0.26f)
                lineToRelative(0.32f, 0.2f)
                lineTo(89.0f, 55.25f)
                verticalLineTo(38.0f)
                horizontalLineTo(69.0f)
                close()
                moveTo(81.68f, 42.0f)
                lineToRelative(-2.18f, 2.0f)
                horizontalLineTo(63.68f)
                lineToRelative(1.76f, -2.0f)
                horizontalLineTo(81.68f)
                close()
                moveTo(42.0f, 44.0f)
                horizontalLineToRelative(-4.0f)
                horizontalLineTo(25.0f)
                verticalLineTo(31.0f)
                horizontalLineToRelative(19.53f)
                lineToRelative(5.3f, 4.0f)
                lineToRelative(-2.84f, 3.4f)
                lineToRelative(3.27f, 2.72f)
                lineToRelative(5.69f, -6.83f)
                lineTo(51.59f, 31.0f)
                horizontalLineTo(58.0f)
                verticalLineToRelative(13.0f)
                horizontalLineTo(42.0f)
                close()
                moveTo(66.0f, 26.02f)
                verticalLineToRelative(9.29f)
                lineToRelative(-4.0f, 4.55f)
                verticalLineTo(29.47f)
                lineTo(66.0f, 26.02f)
                close()
                moveTo(31.55f, 24.0f)
                horizontalLineToRelative(30.28f)
                lineToRelative(-3.49f, 3.0f)
                horizontalLineTo(27.67f)
                lineTo(31.55f, 24.0f)
                close()
                moveTo(11.26f, 42.0f)
                horizontalLineTo(21.0f)
                verticalLineToRelative(2.0f)
                horizontalLineTo(8.25f)
                lineTo(11.26f, 42.0f)
                close()
                moveTo(38.0f, 62.0f)
                horizontalLineTo(15.91f)
                lineToRelative(5.45f, -6.43f)
                lineToRelative(-3.24f, -2.75f)
                lineToRelative(-6.73f, 7.95f)
                lineTo(10.25f, 62.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(48.0f)
                horizontalLineToRelative(16.0f)
                horizontalLineToRelative(17.0f)
                verticalLineTo(62.0f)
                close()
                moveTo(78.0f, 62.0f)
                horizontalLineTo(42.0f)
                verticalLineTo(48.0f)
                horizontalLineToRelative(7.96f)
                lineToRelative(1.09f, 9.62f)
                lineToRelative(10.91f, -2.73f)
                lineToRelative(7.25f, 3.54f)
                lineToRelative(1.86f, -3.82f)
                lineToRelative(-8.65f, -4.22f)
                lineToRelative(-7.7f, 1.92f)
                lineTo(54.24f, 48.0f)
                horizontalLineTo(62.0f)
                horizontalLineToRelative(16.0f)
                verticalLineTo(62.0f)
                close()
                moveTo(82.0f, 58.9f)
                verticalLineToRelative(-10.42f)
                lineToRelative(3.0f, -3.82f)
                verticalLineToRelative(9.44f)
                lineTo(82.0f, 58.9f)
                close()
            }
        }
        .build()
        return _brick!!
    }

private var _brick: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Icons.Brick, contentDescription = "")
    }
}
