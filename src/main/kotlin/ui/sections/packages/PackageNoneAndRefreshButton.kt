package ui.sections.packages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Package
import org.koin.compose.koinInject
import store.AppStore
import terminal.commands.PmCommands
import ui.widgets.Dropdown
import ui.widgets.buttons.BtnWithText

@Composable
fun PackageNoneAndRefreshButton(
    model: AppStore = koinInject()
) {
    val state = model.state
    var selectedOption by remember { mutableStateOf(PmCommands.optionsList[0]) }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        val nonePackage = Package(AppStore.PACKAGE_NONE)
        PackageItem(
            nonePackage,
            state.selectedPackage == AppStore.PACKAGE_NONE,
            { model.onPackageClick(nonePackage) },
            false,
            false,
            Modifier.weight(1f)
        )

        Dropdown(
            options = PmCommands.optionsList,
            optionsDetails = PmCommands.optionsListDetails,
            title = "Package type",
            minWidth = 120.dp,
            onOptionSelected = { selectedOption = it }
        )

        BtnWithText(
            icon = Icons.Rounded.Refresh,
            modifier = Modifier.padding(horizontal = 8.dp),
            visible = !state.isPackagesLoading && state.selectedTargetsList.isNotEmpty(),
            enabled = true,
            onClick = { model.onGetPackageListClick(selectedOption) },
            description = "Get Packages List",
            width = 120.dp,
        )
    }
}
