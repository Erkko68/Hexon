package eric.bitria.hexon.ui.icons.dice

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Icons._5: ImageVector
    get() {
        if (__5 != null) {
            return __5!!
        }
        __5 = Builder(name = "_5", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp, viewportWidth
                = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(10.0f, 8.104f)
                curveTo(8.896f, 8.104f, 8.0f, 9.0f, 8.0f, 10.104f)
                curveTo(8.0f, 11.209f, 8.896f, 12.104f, 10.0f, 12.104f)
                curveTo(11.104f, 12.104f, 12.0f, 11.209f, 12.0f, 10.104f)
                curveTo(12.0f, 9.0f, 11.104f, 8.104f, 10.0f, 8.104f)
                lineTo(10.0f, 8.104f)
                close()
                moveTo(18.0f, 3.0f)
                curveTo(18.0f, 2.448f, 17.552f, 2.0f, 17.0f, 2.0f)
                lineTo(3.0f, 2.0f)
                curveTo(2.448f, 2.0f, 2.0f, 2.448f, 2.0f, 3.0f)
                lineTo(2.0f, 17.0f)
                curveTo(2.0f, 17.553f, 2.448f, 18.0f, 3.0f, 18.0f)
                lineTo(17.0f, 18.0f)
                curveTo(17.552f, 18.0f, 18.0f, 17.553f, 18.0f, 17.0f)
                lineTo(18.0f, 3.0f)
                close()
                moveTo(20.0f, 2.0f)
                lineTo(20.0f, 18.0f)
                curveTo(20.0f, 19.104f, 19.105f, 20.0f, 18.0f, 20.0f)
                lineTo(2.0f, 20.0f)
                curveTo(0.896f, 20.0f, 0.0f, 19.104f, 0.0f, 18.0f)
                lineTo(0.0f, 2.0f)
                curveTo(0.0f, 0.896f, 0.896f, -0.0f, 2.0f, -0.0f)
                lineTo(18.0f, -0.0f)
                curveTo(19.105f, -0.0f, 20.0f, 0.896f, 20.0f, 2.0f)
                lineTo(20.0f, 2.0f)
                close()
                moveTo(6.0f, 8.104f)
                curveTo(7.104f, 8.104f, 8.0f, 7.209f, 8.0f, 6.104f)
                curveTo(8.0f, 5.0f, 7.104f, 4.104f, 6.0f, 4.104f)
                curveTo(4.896f, 4.104f, 4.0f, 5.0f, 4.0f, 6.104f)
                curveTo(4.0f, 7.209f, 4.896f, 8.104f, 6.0f, 8.104f)
                lineTo(6.0f, 8.104f)
                close()
                moveTo(6.0f, 12.104f)
                curveTo(4.896f, 12.104f, 4.0f, 13.0f, 4.0f, 14.104f)
                curveTo(4.0f, 15.209f, 4.896f, 16.104f, 6.0f, 16.104f)
                curveTo(7.104f, 16.104f, 8.0f, 15.209f, 8.0f, 14.104f)
                curveTo(8.0f, 13.0f, 7.104f, 12.104f, 6.0f, 12.104f)
                lineTo(6.0f, 12.104f)
                close()
                moveTo(14.0f, 4.104f)
                curveTo(12.896f, 4.104f, 12.0f, 5.0f, 12.0f, 6.104f)
                curveTo(12.0f, 7.209f, 12.896f, 8.104f, 14.0f, 8.104f)
                curveTo(15.104f, 8.104f, 16.0f, 7.209f, 16.0f, 6.104f)
                curveTo(16.0f, 5.0f, 15.104f, 4.104f, 14.0f, 4.104f)
                lineTo(14.0f, 4.104f)
                close()
                moveTo(16.0f, 14.104f)
                curveTo(16.0f, 15.209f, 15.104f, 16.104f, 14.0f, 16.104f)
                curveTo(12.896f, 16.104f, 12.0f, 15.209f, 12.0f, 14.104f)
                curveTo(12.0f, 13.0f, 12.896f, 12.104f, 14.0f, 12.104f)
                curveTo(15.104f, 12.104f, 16.0f, 13.0f, 16.0f, 14.104f)
                lineTo(16.0f, 14.104f)
                close()
            }
        }
        .build()
        return __5!!
    }

private var __5: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Icons._5, contentDescription = "")
    }
}
