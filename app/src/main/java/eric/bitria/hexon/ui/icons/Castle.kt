package eric.bitria.hexon.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
        _castle = Builder(name = "Castle", defaultWidth = 512.0.dp, defaultHeight = 512.0.dp,
                viewportWidth = 16.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(3.0f, 1.0f)
                verticalLineTo(4.0f)
                lineTo(4.667f, 5.667f)
                lineTo(4.2f, 8.0f)
                horizontalLineTo(2.0f)
                verticalLineTo(6.0f)
                horizontalLineTo(0.0f)
                verticalLineTo(15.0f)
                horizontalLineTo(16.0f)
                verticalLineTo(6.0f)
                horizontalLineTo(14.0f)
                verticalLineTo(8.0f)
                horizontalLineTo(11.8f)
                lineTo(11.333f, 5.667f)
                lineTo(13.0f, 4.0f)
                verticalLineTo(1.0f)
                horizontalLineTo(11.0f)
                verticalLineTo(3.0f)
                horizontalLineTo(9.0f)
                verticalLineTo(1.0f)
                horizontalLineTo(7.0f)
                verticalLineTo(3.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(1.0f)
                horizontalLineTo(3.0f)
                close()
            }
        }
        .build()
        return _castle!!
    }

private var _castle: ImageVector? = null

@Preview(showBackground = true)
@Composable
fun CastlePreview() {
    Icon(
        imageVector = Icons.Castle,
        contentDescription = "House Icon",
        tint = MaterialTheme.colorScheme.primary
    )
}