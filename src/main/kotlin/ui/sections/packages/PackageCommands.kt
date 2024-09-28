package ui.sections.packages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import store.AppStore
import ui.theme.Dimensions
import ui.widgets.buttons.BtnWithText

@Composable
fun PackageCommands(
    modifier: Modifier = Modifier,
    model: AppStore = koinInject()
) {
    val state = model.state
    val isPackageSelected = state.selectedPackage != AppStore.PACKAGE_NONE
    val isDeviceSelected = state.selectedTargetsList.isNotEmpty()

    Column(
        modifier = modifier.fillMaxHeight().padding(vertical = 6.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BtnWithText(
            enabled = isPackageSelected,
            onClick = { model.onOpenClick() },
            icon = Icons.Rounded.CheckCircle,
            description = "Open",
            width = Dimensions.packageCommandsBtnWidth,
        )
        BtnWithText(
            enabled = isPackageSelected,
            onClick = { model.onCloseClick() },
            icon = Icons.Rounded.CheckCircle,
            description = "Close",
            width = Dimensions.packageCommandsBtnWidth,
        )
        BtnWithText(
            enabled = isPackageSelected,
            onClick = { model.onRestartClick() },
            icon = Icons.Rounded.CheckCircle,
            description = "Restart",
            width = Dimensions.packageCommandsBtnWidth,
        )
        BtnWithText(
            enabled = isPackageSelected,
            onClick = { model.onClearDataClick() },
            icon = Icons.Rounded.CheckCircle,
            description = "Clear Data",
            width = Dimensions.packageCommandsBtnWidth,
        )
        BtnWithText(
            enabled = isPackageSelected,
            onClick = { model.onClearAndRestartClick() },
            icon = Icons.Rounded.CheckCircle,
            description = "Clear&Restart",
            width = Dimensions.packageCommandsBtnWidth,
        )
        BtnWithText(
            enabled = isPackageSelected,
            onClick = { model.onUninstallClick() },
            icon = Icons.Rounded.CheckCircle,
            description = "Uninstall",
            width = Dimensions.packageCommandsBtnWidth,
        )
        BtnWithText(
            enabled = isPackageSelected && isDeviceSelected,
            onClick = { model.onApkPath() },
            icon = Icons.Rounded.CheckCircle,
            description = "APK Path",
            width = Dimensions.packageCommandsBtnWidth,
        )
    }
}
