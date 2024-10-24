package ui.sections.packages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.BrandAndroid
import compose.icons.tablericons.Plus
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.packages_btn_add
import net.alexandroid.adbugger.adbugger.generated.resources.packages_btn_get_activities
import net.alexandroid.adbugger.adbugger.generated.resources.packages_btn_launch
import net.alexandroid.adbugger.adbugger.generated.resources.packages_label_key
import net.alexandroid.adbugger.adbugger.generated.resources.packages_label_selected_activity
import net.alexandroid.adbugger.adbugger.generated.resources.packages_label_value
import net.alexandroid.adbugger.adbugger.generated.resources.packages_table_columns
import net.alexandroid.adbugger.adbugger.generated.resources.packages_title_type
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore
import terminal.commands.AmCommands
import ui.theme.MyColors
import ui.widgets.Dropdown
import ui.widgets.Table
import ui.widgets.TextFieldX
import ui.widgets.buttons.BtnWithText

@Composable
fun LauncherPackagesTab(model: AppStore = koinInject()) {

    var selectedActivity by remember { mutableStateOf(TextFieldValue("")) }
    var key by remember { mutableStateOf(TextFieldValue("")) }
    var value by remember { mutableStateOf(TextFieldValue("")) }
    var selectedType by remember { mutableStateOf("String") }


    val extrasListOfList by remember(model.state.launchExtras) {
        derivedStateOf {
            model.state.launchExtras.map { it.toList() }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            BtnWithText(
                icon = TablerIcons.BrandAndroid,
                onClick = { model.onGetPackageActivities() },
                description = stringResource(Res.string.packages_btn_get_activities),
                width = 80.dp,
                modifier = Modifier.padding(end = 8.dp)
            )
            TextFieldX(
                value = selectedActivity,
                onValueChange = { newText ->
                    selectedActivity = newText
                },
                label = stringResource(Res.string.packages_label_selected_activity),
                modifier = Modifier.weight(1f),
                padding = PaddingValues(top = 6.dp)
            )
            BtnWithText(
                icon = TablerIcons.BrandAndroid,
                onClick = { model.testLaunch(selectedActivity.text) },
                description = stringResource(Res.string.packages_btn_launch),
                width = 70.dp,
            )
        }

        Row(modifier = Modifier.padding(vertical = 4.dp)) {
            TextFieldX(
                value = key,
                onValueChange = { newText ->
                    key = newText
                },
                padding = PaddingValues(top = 7.dp),
                label = stringResource(Res.string.packages_label_key),
                modifier = Modifier.weight(0.3f),
            )
            TextFieldX(
                value = value,
                onValueChange = { newText ->
                    value = newText
                },
                padding = PaddingValues(top = 7.dp, start = 6.dp, end = 6.dp),
                label = stringResource(Res.string.packages_label_value),
                modifier = Modifier.weight(0.3f),
            )

            Dropdown(
                options = AmCommands.typesLists,
                optionsDetails = AmCommands.typesListsDetails,
                title = stringResource(Res.string.packages_title_type),
                onOptionSelected = { selectedType = it },
                modifier = Modifier.padding(top = 1.dp),
                contentModifier = Modifier.background(MyColors.bg2),
                titleBgColor = MyColors.bg2
            )

            BtnWithText(
                icon = TablerIcons.Plus,
                onClick = { model.addExtra(key.text, value.text, selectedType) },
                description = stringResource(Res.string.packages_btn_add),
                width = 70.dp,
            )
        }

        Table(
            3,
            stringArrayResource(Res.array.packages_table_columns),
            extrasListOfList,
            listOf(0.4f, 0.4f, 0.2f),
            listOf(0, 1),
            deleteAction = { model.onExtraDeleteClick(it[0]) }
        )
    }

}