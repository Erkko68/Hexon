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

val Icons.House: ImageVector
    get() {
        if (_house != null) {
            return _house!!
        }
        _house = Builder(name = "House", defaultWidth = 100.0.dp, defaultHeight = 100.0.dp,
                viewportWidth = 100.0f, viewportHeight = 100.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(15.7f, 21.2f)
                horizontalLineToRelative(11.7f)
                verticalLineTo(31.0f)
                lineToRelative(-11.7f, 9.9f)
                verticalLineTo(21.2f)
                close()
                moveTo(13.5f, 52.1f)
                lineTo(10.0f, 48.5f)
                lineToRelative(5.4f, -4.5f)
                curveToRelative(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
                lineToRelative(13.8f, -11.7f)
                curveToRelative(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
                lineTo(50.0f, 14.7f)
                lineToRelative(40.0f, 33.9f)
                lineToRelative(-3.5f, 3.5f)
                lineTo(50.7f, 22.6f)
                curveToRelative(-0.2f, -0.2f, -0.4f, -0.2f, -0.7f, -0.2f)
                reflectiveCurveToRelative(-0.5f, 0.1f, -0.7f, 0.2f)
                lineTo(13.5f, 52.1f)
                close()
                moveTo(27.6f, 81.7f)
                curveToRelative(1.2f, -1.2f, 1.9f, -2.9f, 1.9f, -4.8f)
                curveToRelative(0.0f, -1.9f, -0.7f, -3.5f, -1.9f, -4.8f)
                horizontalLineToRelative(12.7f)
                verticalLineToRelative(9.6f)
                horizontalLineTo(27.6f)
                close()
                moveTo(40.4f, 62.1f)
                verticalLineTo(70.0f)
                horizontalLineTo(27.6f)
                curveToRelative(1.2f, -1.2f, 1.9f, -2.9f, 1.9f, -4.8f)
                curveToRelative(0.0f, -1.9f, -0.7f, -3.5f, -1.9f, -4.8f)
                horizontalLineToRelative(12.9f)
                curveTo(40.4f, 61.0f, 40.4f, 61.5f, 40.4f, 62.1f)
                close()
                moveTo(42.5f, 62.1f)
                curveToRelative(0.0f, -3.4f, 2.8f, -6.2f, 6.2f, -6.2f)
                horizontalLineToRelative(2.6f)
                curveToRelative(3.4f, 0.0f, 6.2f, 2.8f, 6.2f, 6.2f)
                verticalLineToRelative(19.6f)
                horizontalLineToRelative(-15.0f)
                verticalLineTo(62.1f)
                close()
                moveTo(59.6f, 72.1f)
                horizontalLineTo(72.0f)
                curveToRelative(-1.2f, 1.2f, -1.9f, 2.9f, -1.9f, 4.8f)
                curveToRelative(0.0f, 1.9f, 0.7f, 3.5f, 1.9f, 4.8f)
                horizontalLineTo(59.6f)
                verticalLineTo(72.1f)
                close()
                moveTo(59.6f, 70.0f)
                verticalLineToRelative(-7.9f)
                curveToRelative(0.0f, -0.6f, -0.1f, -1.1f, -0.2f, -1.6f)
                horizontalLineTo(72.0f)
                curveToRelative(-1.2f, 1.2f, -1.9f, 2.9f, -1.9f, 4.8f)
                curveToRelative(0.0f, 1.9f, 0.7f, 3.5f, 1.9f, 4.8f)
                horizontalLineTo(59.6f)
                close()
                moveTo(58.7f, 58.3f)
                curveToRelative(-1.4f, -2.7f, -4.2f, -4.6f, -7.4f, -4.6f)
                horizontalLineToRelative(-2.6f)
                curveToRelative(-3.2f, 0.0f, -6.0f, 1.9f, -7.4f, 4.6f)
                horizontalLineTo(27.6f)
                curveToRelative(1.2f, -1.2f, 1.9f, -2.9f, 1.9f, -4.8f)
                curveToRelative(0.0f, -1.9f, -0.7f, -3.5f, -1.9f, -4.8f)
                horizontalLineTo(72.0f)
                curveToRelative(-1.2f, 1.2f, -1.9f, 2.9f, -1.9f, 4.8f)
                curveToRelative(0.0f, 1.9f, 0.7f, 3.5f, 1.9f, 4.8f)
                horizontalLineTo(58.7f)
                close()
                moveTo(35.1f, 37.1f)
                horizontalLineToRelative(29.7f)
                curveToRelative(0.0f, 0.0f, 0.0f, 0.0f, 0.1f, 0.0f)
                lineToRelative(11.6f, 9.6f)
                horizontalLineTo(23.5f)
                lineTo(35.1f, 37.1f)
                close()
                moveTo(37.7f, 34.9f)
                lineTo(50.0f, 24.8f)
                lineToRelative(12.3f, 10.1f)
                horizontalLineTo(37.7f)
                close()
                moveTo(17.9f, 53.5f)
                curveToRelative(0.0f, -2.6f, 2.1f, -4.8f, 4.8f, -4.8f)
                reflectiveCurveToRelative(4.8f, 2.1f, 4.8f, 4.8f)
                curveToRelative(0.0f, 2.6f, -2.1f, 4.8f, -4.8f, 4.8f)
                reflectiveCurveTo(17.9f, 56.2f, 17.9f, 53.5f)
                close()
                moveTo(17.9f, 65.2f)
                curveToRelative(0.0f, -2.6f, 2.1f, -4.8f, 4.8f, -4.8f)
                reflectiveCurveToRelative(4.8f, 2.1f, 4.8f, 4.8f)
                curveToRelative(0.0f, 2.6f, -2.1f, 4.8f, -4.8f, 4.8f)
                reflectiveCurveTo(17.9f, 67.8f, 17.9f, 65.2f)
                close()
                moveTo(17.9f, 76.9f)
                curveToRelative(0.0f, -2.6f, 2.1f, -4.8f, 4.8f, -4.8f)
                reflectiveCurveToRelative(4.8f, 2.1f, 4.8f, 4.8f)
                reflectiveCurveToRelative(-2.1f, 4.8f, -4.8f, 4.8f)
                reflectiveCurveTo(17.9f, 79.5f, 17.9f, 76.9f)
                close()
                moveTo(85.9f, 85.6f)
                horizontalLineTo(14.1f)
                verticalLineToRelative(-1.8f)
                horizontalLineToRelative(27.3f)
                horizontalLineToRelative(17.1f)
                horizontalLineToRelative(27.3f)
                verticalLineTo(85.6f)
                close()
                moveTo(81.8f, 76.9f)
                curveToRelative(0.0f, 2.6f, -2.1f, 4.8f, -4.8f, 4.8f)
                reflectiveCurveToRelative(-4.8f, -2.1f, -4.8f, -4.8f)
                reflectiveCurveToRelative(2.1f, -4.8f, 4.8f, -4.8f)
                reflectiveCurveTo(81.8f, 74.3f, 81.8f, 76.9f)
                close()
                moveTo(81.8f, 65.2f)
                curveToRelative(0.0f, 2.6f, -2.1f, 4.8f, -4.8f, 4.8f)
                reflectiveCurveToRelative(-4.8f, -2.1f, -4.8f, -4.8f)
                curveToRelative(0.0f, -2.6f, 2.1f, -4.8f, 4.8f, -4.8f)
                reflectiveCurveTo(81.8f, 62.6f, 81.8f, 65.2f)
                close()
                moveTo(77.0f, 58.3f)
                curveToRelative(-2.6f, 0.0f, -4.8f, -2.1f, -4.8f, -4.8f)
                curveToRelative(0.0f, -2.6f, 2.1f, -4.8f, 4.8f, -4.8f)
                reflectiveCurveToRelative(4.8f, 2.1f, 4.8f, 4.8f)
                curveTo(81.8f, 56.2f, 79.7f, 58.3f, 77.0f, 58.3f)
                close()
            }
        }
        .build()
        return _house!!
    }

private var _house: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Icons.House, contentDescription = "")
    }
}
