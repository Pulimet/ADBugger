package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import store.AppStore
import ui.sections.DeviceListSection
import ui.sections.PackageListSection

@Composable
@Preview
fun MainContent() {
    val coroutineScope = rememberCoroutineScope()

    val model = remember { AppStore() }
    val state = model.state

    LaunchedEffect(Unit) {
        model.onLaunchedEffect(coroutineScope)
    }

    MaterialTheme {
        Column {
            Row {
                DeviceListSection(
                    coroutineScope, model, state,
                    modifier = Modifier.fillMaxWidth(0.5f).height(300.dp)
                )
                PackageListSection(
                    coroutineScope, model, state,
                    modifier = Modifier.fillMaxWidth().height(300.dp)
                )
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { model.onOpenClick(coroutineScope) }) { Text(text = "Open") }
                Button(onClick = { model.onCloseClick(coroutineScope) }) { Text(text = "Close") }
            }
        }
    }
}



