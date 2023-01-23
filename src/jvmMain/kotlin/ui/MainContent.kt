package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import store.AppStore

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
        Column(
            modifier = Modifier.fillMaxSize().background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { model.onGetDevicesListClick(coroutineScope) }) { Text(text = "Get Devices List") }

            val listState = rememberLazyListState()
            LazyColumn(state = listState) {
                items(state.devicesList) { item ->
                    Text("${item.serial} (${item.state.name})")
                }
            }

            //Button(onClick = { model.onOpenClick(coroutineScope) }) { Text(text = "Open") }
            //Button(onClick = { model.onCloseClick(coroutineScope) }) { Text(text = "Close") }

            //LoadingSpinner()
        }
    }
}
