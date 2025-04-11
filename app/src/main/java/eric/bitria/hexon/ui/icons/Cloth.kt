package eric.bitria.hexon.ui.icons

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

val Icons.Cloth: ImageVector
    get() {
        if (_cloth != null) {
            return _cloth!!
        }
        _cloth = Builder(name = "Cloth", defaultWidth = 512.0.dp, defaultHeight = 512.0.dp,
                viewportWidth = 512.0f, viewportHeight = 512.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(165.45f, 34.79f)
                curveToRelative(-23.17f, 0.02f, -45.63f, 12.97f, -54.61f, 36.32f)
                lineToRelative(-83.67f, 326.17f)
                curveToRelative(-12.67f, 94.54f, 81.04f, 88.74f, 137.96f, 65.4f)
                curveToRelative(81.42f, -33.4f, 181.72f, -29.21f, 263.24f, -8.26f)
                lineToRelative(6.45f, -17.22f)
                curveToRelative(-7.38f, -2.64f, -15.33f, -5.99f, -22.25f, -8.04f)
                curveToRelative(0.47f, -4.36f, 0.95f, -8.72f, 1.44f, -13.07f)
                lineToRelative(23.04f, 4.12f)
                lineToRelative(3.23f, -18.1f)
                curveToRelative(-8.07f, -1.44f, -16.15f, -2.88f, -24.22f, -4.33f)
                curveToRelative(0.62f, -5.4f, 1.24f, -10.8f, 1.87f, -16.19f)
                lineToRelative(22.13f, 3.28f)
                lineToRelative(2.69f, -18.19f)
                curveToRelative(-7.55f, -1.12f, -15.1f, -2.24f, -22.65f, -3.36f)
                curveToRelative(0.46f, -3.77f, 0.91f, -7.53f, 1.38f, -11.29f)
                curveToRelative(7.61f, 1.09f, 15.23f, 2.18f, 22.85f, 3.27f)
                lineToRelative(2.61f, -18.2f)
                lineToRelative(-23.16f, -3.32f)
                curveToRelative(0.46f, -3.59f, 1.29f, -9.99f, 1.76f, -13.58f)
                lineToRelative(22.78f, 2.55f)
                lineToRelative(2.05f, -17.57f)
                curveToRelative(-7.47f, -0.83f, -14.94f, -1.67f, -22.4f, -2.51f)
                curveToRelative(0.78f, -5.77f, 1.92f, -11.18f, 2.73f, -16.94f)
                curveToRelative(7.67f, 1.12f, 15.34f, 2.24f, 23.01f, 3.37f)
                lineToRelative(2.31f, -17.14f)
                curveToRelative(-7.68f, -1.13f, -15.37f, -2.25f, -23.05f, -3.37f)
                curveToRelative(0.79f, -5.41f, 1.25f, -10.13f, 2.07f, -15.54f)
                curveToRelative(7.07f, 1.26f, 14.15f, 2.53f, 21.22f, 3.79f)
                lineToRelative(3.23f, -18.1f)
                lineToRelative(-21.65f, -3.87f)
                curveToRelative(0.74f, -4.68f, 1.47f, -9.35f, 2.23f, -14.03f)
                curveToRelative(6.98f, 1.67f, 13.95f, 3.35f, 20.93f, 5.02f)
                lineTo(465.28f, 208.0f)
                curveToRelative(-7.4f, -1.78f, -14.8f, -3.55f, -22.2f, -5.33f)
                arcToRelative(2809.25f, 2809.25f, 0.0f, false, true, 2.13f, -12.48f)
                curveToRelative(6.98f, 1.58f, 13.96f, 3.16f, 20.94f, 4.75f)
                lineToRelative(4.06f, -17.93f)
                curveToRelative(-7.27f, -1.65f, -14.54f, -3.3f, -21.82f, -4.95f)
                curveToRelative(0.77f, -4.27f, 1.55f, -8.53f, 2.34f, -12.81f)
                lineToRelative(20.74f, 5.15f)
                lineToRelative(4.43f, -17.84f)
                lineToRelative(-21.75f, -5.41f)
                curveToRelative(0.74f, -3.85f, 1.49f, -7.7f, 2.25f, -11.55f)
                lineToRelative(20.28f, 5.01f)
                lineToRelative(4.41f, -17.85f)
                lineToRelative(-21.06f, -5.21f)
                arcToRelative(2444.47f, 2444.47f, 0.0f, false, true, 2.57f, -12.37f)
                curveToRelative(8.39f, 2.41f, 13.13f, 2.36f, 21.41f, 4.99f)
                lineTo(486.0f, 88.46f)
                curveToRelative(-83.81f, -26.78f, -179.25f, -33.22f, -244.19f, -6.45f)
                curveToRelative(-24.34f, 114.04f, -37.31f, 221.4f, -68.03f, 338.64f)
                curveToRelative(-3.41f, 13.0f, -14.47f, 21.89f, -27.34f, 28.06f)
                curveToRelative(-27.0f, 11.61f, -64.03f, 13.78f, -84.63f, -4.91f)
                curveToRelative(-10.97f, -10.34f, -16.17f, -27.04f, -12.47f, -47.58f)
                curveToRelative(2.3f, -12.76f, 10.88f, -21.99f, 20.83f, -26.38f)
                curveToRelative(19.75f, -7.07f, 43.49f, -4.25f, 58.89f, 7.95f)
                curveToRelative(12.46f, 9.3f, 12.32f, 38.28f, -3.88f, 31.82f)
                curveToRelative(-9.64f, -6.17f, -1.96f, -11.85f, -8.61f, -17.38f)
                curveToRelative(-11.6f, -7.43f, -26.42f, -10.87f, -38.97f, -5.57f)
                curveToRelative(-5.56f, 2.45f, -8.89f, 5.74f, -10.17f, 12.82f)
                curveToRelative(-2.94f, 16.29f, 0.69f, 25.0f, 6.99f, 30.93f)
                curveToRelative(18.33f, 13.49f, 45.28f, 10.49f, 64.07f, 1.71f)
                curveToRelative(10.05f, -4.82f, 16.28f, -11.44f, 17.51f, -16.15f)
                curveToRelative(30.54f, -116.52f, 43.44f, -224.12f, 68.29f, -339.96f)
                curveToRelative(-11.8f, -28.34f, -35.67f, -41.25f, -58.84f, -41.22f)
                close()
            }
        }
        .build()
        return _cloth!!
    }

private var _cloth: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Icons.Cloth, contentDescription = "")
    }
}