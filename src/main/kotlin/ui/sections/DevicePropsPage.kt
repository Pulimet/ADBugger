package ui.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.Refresh
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.CardX
import ui.widgets.SearchView
import ui.widgets.Table
import ui.widgets.buttons.BtnIcon

// TODO Parse each string in a permissions list and show UI friendly with Table Widget

@Composable
fun DevicePropsPage(modifier: Modifier = Modifier, model: AppStore = koinInject()) {
    var textState by remember { mutableStateOf("") }

    val list by remember(model.state.deviceProps) {
        derivedStateOf {
            model.state.deviceProps.map {
                var splitList = it.split(":")
                if (splitList.size > 2) {
                    splitList = listOf(splitList[0], splitList.joinToString(" : "))
                }

                splitList.map { cell ->
                    val trimmed = cell.trim()

                    if (trimmed.length > 2) {
                        try {
                            trimmed.substring(1, trimmed.length - 1)
                        } catch (e: Exception) {
                            "Exception on $trimmed"
                        }
                    } else {
                        trimmed
                    }
                }
            }
        }
    }

    CardX(modifier = modifier) {
        Column(modifier = Modifier.padding(Dimensions.cardPadding).fillMaxSize()) {
            Text(
                text = "Device properties",
                fontSize = Dimensions.titleFontSize,
                textAlign = TextAlign.Center,
                color = Color.LightGray,
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
            )

            if (model.state.deviceProps.isNotEmpty()) {
                SearchView {
                    textState = it
                }
                Table(
                    columns = 2,
                    headerTitlesList = listOf("Property", "Value"),
                    tableList = list,
                    weightList = listOf(0.5f, 0.5f),
                    filter = textState
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    BtnIcon(
                        icon = TablerIcons.Refresh,
                        description = "Get Props",
                        onClick = { model.onGetDeviceProps() }
                    )
                }
            }
        }
        if (model.state.deviceProps.isNotEmpty()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                BtnIcon(
                    icon = TablerIcons.Refresh,
                    description = "Get Props",
                    buttonSize = Dimensions.btnSizeSmall,
                    iconSize = Dimensions.btnIconSizeSmall,
                    modifier = Modifier.padding(16.dp),
                    onClick = { model.onGetDeviceProps() }
                )
            }
        }
    }
}
