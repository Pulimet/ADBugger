package ui.sections.target

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import store.AppStore

@Composable
fun TargetConnectionTab(model: AppStore = koinInject()) {
    Text("Connection")
}
