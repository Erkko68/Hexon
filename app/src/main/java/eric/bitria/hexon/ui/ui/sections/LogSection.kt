package eric.bitria.hexon.ui.ui.sections

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.view.models.LogEntry

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LogSection(
    logs: List<LogEntry>,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val isVertical = maxHeight > maxWidth
        val logHeight = if (isVertical) maxHeight * 0.5f else maxHeight
        val maxLines = if (maxWidth > 300.dp) 1 else 2

        LazyColumn(
            modifier = Modifier
                .height(logHeight)
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp)
        ) {
            items(logs.asReversed()) { log ->
                val displayText = if (maxLines == 1) log.text.replace("\n", "") else log.text
                Text(
                    text = displayText,
                    color = log.color.takeIf { it != Color.Unspecified }
                        ?: MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = maxLines
                )
            }
        }
    }
}