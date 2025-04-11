package eric.bitria.hexon.ui.icons.buildings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
        _house = Builder(name = "House", defaultWidth = 512.0.dp, defaultHeight = 512.0.dp,
                viewportWidth = 256.0f, viewportHeight = 256.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(224.0f, 115.54f)
                verticalLineTo(208.0f)
                arcToRelative(16.02f, 16.02f, 0.0f, false, true, -16.0f, 16.0f)
                horizontalLineTo(48.0f)
                arcToRelative(16.02f, 16.02f, 0.0f, false, true, -16.0f, -16.0f)
                verticalLineTo(115.54f)
                arcToRelative(16.03f, 16.03f, 0.0f, false, true, 5.24f, -11.84f)
                lineToRelative(79.99f, -72.73f)
                arcToRelative(15.94f, 15.94f, 0.0f, false, true, 21.53f, -0.0f)
                lineToRelative(80.01f, 72.74f)
                lineToRelative(0.0f, 0.0f)
                arcTo(16.03f, 16.03f, 0.0f, false, true, 224.0f, 115.54f)
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