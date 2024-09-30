package ui.widgets

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import compose.icons.TablerIcons
import compose.icons.tablericons.ClearAll
import ui.theme.Dimensions
import ui.theme.MyColors
import ui.widgets.buttons.BtnIcon
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
    modifier: Modifier = Modifier,
    deleteAction: ((List<String>) -> Unit)? = null
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
            item {
                TableHeader(headerTitlesList, weightList, deleteAction)
            }
            items(filteredList) { item ->
                TableRow(item, weightList, clipboard, copyColumnsList, deleteAction)
            }
        }
        TableScroll(filteredList, listState)
    }
}

@Composable
fun TableHeader(
    headerTitles: List<String>,
    weight: List<Float>,
    deleteAction: ((List<String>) -> Unit)?
) {
    Row(Modifier.background(MyColors.bg)) {
        headerTitles.forEachIndexed { index, item ->
            TableCell(text = item, weight = weight[index])
        }
        deleteAction?.let {
            TableCell(text = "Delete", 0f)
        }
    }
}

@Composable
fun TableRow(
    items: List<String>,
    weight: List<Float>,
    clipboard: Clipboard?,
    copyColumnsList: List<Int>,
    deleteAction: ((List<String>) -> Unit)?
) {
    Row(
        Modifier.fillMaxWidth().clickable { onRowClock(copyColumnsList, items, clipboard) }
    ) {
        items.forEachIndexed { index, item ->
            if (index < weight.size) {
                TableCell(text = item, weight = weight[index])
            }
        }
        DeleteButton(deleteAction, items)
    }
}

private fun onRowClock(copyColumnsList: List<Int>, list: List<String>, clipboard: Clipboard?) {
    val contentToCopy: String = if (copyColumnsList.isEmpty()) {
        list.joinToString(" - ")
    } else {
        var result = ""
        list.forEachIndexed { index, item ->
            if (copyColumnsList.contains(index)) {
                result += item
            }
        }
        result
    }
    clipboard?.setContents(StringSelection(contentToCopy), null)
}

@Composable
fun RowScope.TableCell(text: String, weight: Float) {
    val modifier = if (weight > 0f) {
        Modifier
            .border(0.5.dp, Color.Gray)
            .weight(weight)
            .padding(4.dp)
    } else {
        Modifier
            .border(0.5.dp, Color.Gray)
            .width(60.dp)
            .padding(4.dp)
    }

    Text(
        text = text,
        modifier = modifier,
        fontSize = Dimensions.subtitleFontSize,
        lineHeight = Dimensions.subtitleFontSize,
        color = Color.LightGray
    )
}

@Composable
fun DeleteButton(deleteAction: ((List<String>) -> Unit)?, items: List<String>) {
    deleteAction?.let {
        Row(
            modifier = Modifier.width(60.dp).border(0.5.dp, Color.Gray),
            horizontalArrangement = Arrangement.Center
        ) {
            BtnIcon(
                TablerIcons.ClearAll,
                onClick = { deleteAction(items) },
                buttonSize = Dimensions.btnSizeSmaller,
                iconSize = Dimensions.btnIconSizeSmaller,
                description = "Delete",
                showTooltip = false
            )
        }
    }
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
