package eric.bitria.hexon.ui.icons.buildings

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

val Icons.Trail: ImageVector
    get() {
        if (_trail != null) {
            return _trail!!
        }
        _trail = Builder(name = "Trail", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 512.0f, viewportHeight = 512.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(149.9f, 27.2f)
                lineTo(34.25f, 56.74f)
                verticalLineToRelative(76.76f)
                lineTo(157.8f, 93.85f)
                lineToRelative(46.7f, -44.67f)
                lineToRelative(-54.6f, -21.98f)
                close()
                moveTo(282.7f, 84.2f)
                curveToRelative(-7.4f, 0.18f, -10.1f, 1.88f, 0.9f, 7.13f)
                curveTo(346.9f, 121.6f, 441.7f, 206.8f, 391.3f, 216.9f)
                curveTo(232.2f, 249.0f, 130.4f, 292.3f, 48.51f, 390.8f)
                curveTo(25.42f, 418.6f, 18.0f, 494.8f, 18.0f, 494.8f)
                horizontalLineToRelative(432.6f)
                reflectiveCurveToRelative(-139.0f, -21.1f, -147.8f, -75.7f)
                curveToRelative(-14.9f, -92.2f, 194.5f, -102.7f, 196.5f, -199.9f)
                curveToRelative(0.9f, -43.2f, -88.3f, -124.99f, -184.4f, -132.52f)
                curveToRelative(-5.6f, -0.44f, -22.7f, -2.71f, -32.2f, -2.48f)
                close()
                moveTo(119.2f, 125.1f)
                lineToRelative(-32.69f, 10.5f)
                verticalLineToRelative(122.2f)
                lineToRelative(35.99f, -10.0f)
                lineToRelative(-3.3f, -122.7f)
                close()
            }
        }
        .build()
        return _trail!!
    }

private var _trail: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Icons.Trail, contentDescription = "")
    }
}