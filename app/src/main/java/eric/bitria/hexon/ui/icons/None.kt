package eric.bitria.hexon.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
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