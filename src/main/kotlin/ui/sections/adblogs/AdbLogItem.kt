package ui.sections.adblogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection

@Composable
fun AdbLogItem(log: String, clipboard: Clipboard) {
    SelectionContainer {
        Text(
            text = log,
            modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 120.dp).clickable {
                clipboard.setContents(StringSelection(log), null)
            },
            color = Color.White,
            fontSize = 12.sp
        )
    }
}
