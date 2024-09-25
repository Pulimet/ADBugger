package ui.widgets.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection

@Composable
fun ItemX(title: String, clipboard: Clipboard?) {
    SelectionContainer {
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth().clickable {
                clipboard?.setContents(StringSelection(title), null)
            },
            color = Color.White,
            fontSize = 12.sp
        )
    }
}
