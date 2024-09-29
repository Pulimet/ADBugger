package ui.sections.packages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.BrandAndroid
import org.koin.compose.koinInject
import store.AppStore
import ui.widgets.TextFieldX
import ui.widgets.buttons.BtnWithText

@Composable
fun LauncherTab(model: AppStore = koinInject()) {

    var selectedActivity by remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier.fillMaxSize()) {
        BtnWithText(
            icon = TablerIcons.BrandAndroid,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { model.onGetPackageActivities() },
            description = "Get Activities",
            width = 120.dp,
        )
        TextFieldX(
            value = selectedActivity,
            onValueChange = { newText ->
                selectedActivity = newText
            },
            label = "Component (ex: com.your.app/com.your.app.MainActivity)",
            modifier = Modifier.fillMaxWidth()
        )
        // TODO Add UI that allows to add extras. A row with textfield for key,value and dropdown with type selection
        // TODO Show a table with added extras with columns: key, value, type, icon to delete a row.
        BtnWithText(
            icon = TablerIcons.BrandAndroid,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { model.testLaunch(selectedActivity.text) },
            description = "Test Launch",
            width = 120.dp,
        )
    }

}