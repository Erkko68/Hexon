package eric.bitria.hexon.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Paper: ImageVector
    get() {
        if (_paper != null) {
            return _paper!!
        }
        _paper = Builder(name = "Paper", defaultWidth = 512.0.dp, defaultHeight = 512.0.dp,
                viewportWidth = 512.0f, viewportHeight = 512.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(210.28f, 18.34f)
                curveToRelative(-21.36f, 43.31f, -84.9f, 72.3f, -146.97f, 101.78f)
                lineTo(181.22f, 156.94f)
                lineTo(54.31f, 142.5f)
                curveToRelative(28.59f, 58.05f, 71.69f, 113.35f, 120.97f, 157.75f)
                lineToRelative(99.31f, 29.91f)
                lineTo(179.0f, 323.31f)
                curveToRelative(-35.16f, 32.77f, -95.2f, 70.74f, -161.5f, 91.78f)
                curveToRelative(88.45f, 40.53f, 161.28f, 46.96f, 280.34f, 77.25f)
                curveTo(378.36f, 453.12f, 415.57f, 425.64f, 470.09f, 382.0f)
                lineToRelative(-149.25f, -42.44f)
                lineToRelative(147.47f, 18.94f)
                curveToRelative(-49.76f, -45.25f, -89.57f, -102.69f, -115.47f, -161.44f)
                lineTo(227.53f, 165.13f)
                lineToRelative(141.06f, 13.59f)
                curveToRelative(55.1f, -20.42f, 85.08f, -49.28f, 124.53f, -102.28f)
                curveToRelative(-97.71f, -20.99f, -177.93f, -45.69f, -282.84f, -58.09f)
                close()
            }
        }
        .build()
        return _paper!!
    }

private var _paper: ImageVector? = null
