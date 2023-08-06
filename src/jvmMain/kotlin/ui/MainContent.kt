package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import store.AppStore
import ui.sections.*
import ui.theme.MyColors

@Composable
@Preview
fun MainContent() {
    val coroutineScope = rememberCoroutineScope()

    val model = remember { AppStore() }
    val state = model.state
    val isPackageSelected = state.selectedPackage != AppStore.NONE
    val isDeviceSelected = state.selectedDevice != AppStore.ALL_DEVICES

    val textInputSendTextState = remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        model.onLaunchedEffect(coroutineScope)
    }

    MaterialTheme(colors = darkColors(background = MyColors.bg, primary = Color.White, secondary = Color.White)) {
        Column {
            DeviceListSection(
                coroutineScope, model, state,
                modifier = Modifier.fillMaxWidth().heightIn(min = 50.dp)
            )
            PackageListSection(
                coroutineScope, model, state,
                modifier = Modifier.fillMaxWidth().heightIn(min = 50.dp)
            )

            SendTextToDevices(textInputSendTextState, model, coroutineScope)
            DeviceCommands(model, coroutineScope, isDeviceSelected)
            PackageCommands(isPackageSelected, model, coroutineScope)
        }
    }
}






