package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
        //Button(onClick = { model.onOpenClick(coroutineScope) }) { Text(text = "Open") }
        //Button(onClick = { model.onCloseClick(coroutineScope) }) { Text(text = "Close") }
        Row {
            DeviceListSection(coroutineScope, model, state)
            PackageListSection(coroutineScope, model, state)
        }
    }
}



