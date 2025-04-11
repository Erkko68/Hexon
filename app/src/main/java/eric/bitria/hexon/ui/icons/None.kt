package eric.bitria.hexon.ui.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Icons.None: ImageVector
    get() {
        if (_none != null) {
            return _none!!
        }
        _none = Builder(
            name = "None",
            defaultWidth = 1.dp,
            defaultHeight = 1.dp,
            viewportWidth = 1f,
            viewportHeight = 1f
        ).build()
        return _none!!
    }

private var _none: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Icons.None, contentDescription = "")
    }
}