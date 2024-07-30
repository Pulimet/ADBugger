package utils

import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection

object Clipboard {
    fun copyAllLogsToClipboard(clipboard: Clipboard, list: List<String>) {
        val logs = list.joinToString("\n")
        clipboard.setContents(StringSelection(logs), null)
    }
}
