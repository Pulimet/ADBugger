package ui.widgets

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.Dimensions
import ui.theme.MyColors
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection

@Composable
fun Table(
    columns: Int,
    headerTitlesList: List<String>,
    tableList: List<List<String>>,
    weightList: List<Float>,
    copyColumnsList: List<Int> = emptyList(),
    filter: String? = null,
    modifier: Modifier = Modifier
) {

    if (tableList.isEmpty() || columns == 0 || headerTitlesList.size != columns || tableList[0].size != columns || weightList.size != columns) {
        return
    }

    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val listState = rememberLazyListState()

    val filteredList by remember(tableList, filter) {
        derivedStateOf {
            tableList.filter {
                filter == null || it.joinToString().contains(filter, ignoreCase = true)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(state = listState) {
            // Header
            item {
                TableHeader(headerTitlesList, weightList)
            }
            items(filteredList) { item ->
                TableRow(item, weightList, clipboard, copyColumnsList)
            }
        }
        TableScroll(filteredList, listState)
    }
}

@Composable
fun TableHeader(headerTitles: List<String>, weight: List<Float>) {
    Row(Modifier.background(MyColors.bg)) {
        headerTitles.forEachIndexed { index, item ->
            TableCell(text = item, weight = weight[index])
        }
    }
}

@Composable
fun TableRow(
    item: List<String>,
    weight: List<Float>,
    clipboard: Clipboard?,
    copyColumnsList: List<Int>
) {
    Row(
        Modifier.fillMaxWidth().clickable {
            val contentToCopy: String = if (copyColumnsList.isEmpty()) {
                item.joinToString(" - ")
            } else {
                var result = ""
                item.forEachIndexed { index, item ->
                    if (copyColumnsList.contains(index)) {
                        result += item
                    }
                }
                result
            }
            clipboard?.setContents(StringSelection(contentToCopy), null)
        },
    ) {
        item.forEachIndexed { index, item ->
            if (index < weight.size) {
                TableCell(text = item, weight = weight[index])
            }
        }
    }
}

@Composable
fun RowScope.TableCell(text: String, weight: Float) {
    Text(
        text = text,
        Modifier
            .border(0.5.dp, Color.Gray)
            .weight(weight)
            .padding(4.dp),
        fontSize = Dimensions.subtitleFontSize,
        lineHeight = Dimensions.subtitleFontSize,
        color = Color.LightGray
    )
}

@Composable
fun BoxScope.TableScroll(filteredList: List<List<String>>, listState: LazyListState) {
    if (filteredList.size > 2) {
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd),
            adapter = rememberScrollbarAdapter(
                scrollState = listState
            )
        )
    }
}
