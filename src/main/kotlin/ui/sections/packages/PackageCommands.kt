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
import compose.icons.TablerIcons
import compose.icons.tablericons.SettingsAutomation
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.apk_path
import net.alexandroid.adbugger.adbugger.generated.resources.clear_ata
import net.alexandroid.adbugger.adbugger.generated.resources.clear_restart
import net.alexandroid.adbugger.adbugger.generated.resources.close
import net.alexandroid.adbugger.adbugger.generated.resources.install
import net.alexandroid.adbugger.adbugger.generated.resources.open
import net.alexandroid.adbugger.adbugger.generated.resources.restart
import net.alexandroid.adbugger.adbugger.generated.resources.uninstall
import org.jetbrains.compose.resources.stringResource
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
            description = stringResource(Res.string.open),
            width = Dimensions.packageCommandsBtnWidth,
        )
        BtnWithText(
            enabled = isPackageSelected,
            onClick = { model.onCloseClick() },
            icon = Icons.Rounded.CheckCircle,
            description = stringResource(Res.string.close),
            width = Dimensions.packageCommandsBtnWidth,
        )
        BtnWithText(
            enabled = isPackageSelected,
            onClick = { model.onRestartClick() },
            icon = Icons.Rounded.CheckCircle,
            description = stringResource(Res.string.restart),
            width = Dimensions.packageCommandsBtnWidth,
        )
        BtnWithText(
            enabled = isPackageSelected,
            onClick = { model.onClearDataClick() },
            icon = Icons.Rounded.CheckCircle,
            description = stringResource(Res.string.clear_ata),
            width = Dimensions.packageCommandsBtnWidth,
        )
        BtnWithText(
            enabled = isPackageSelected,
            onClick = { model.onClearAndRestartClick() },
            icon = Icons.Rounded.CheckCircle,
            description = stringResource(Res.string.clear_restart),
            width = Dimensions.packageCommandsBtnWidth,
        )
        BtnWithText(
            enabled = isPackageSelected,
            onClick = { model.onUninstallClick() },
            icon = Icons.Rounded.CheckCircle,
            description = stringResource(Res.string.uninstall),
            width = Dimensions.packageCommandsBtnWidth,
        )
        BtnWithText(
            enabled = isPackageSelected && isDeviceSelected,
            onClick = { model.onApkPath() },
            icon = Icons.Rounded.CheckCircle,
            description = stringResource(Res.string.apk_path),
            width = Dimensions.packageCommandsBtnWidth,
        )
        BtnWithText(
            icon = TablerIcons.SettingsAutomation,
            onClick = { model.openFilePicker() },
            description = stringResource(Res.string.install),
            width = Dimensions.packageCommandsBtnWidth,
        )
    }
}
