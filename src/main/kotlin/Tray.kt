import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.MenuScope
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberTrayState
import net.alexandroid.adbugger.adbugger.generated.resources.Res
import net.alexandroid.adbugger.adbugger.generated.resources.back
import net.alexandroid.adbugger.adbugger.generated.resources.clear_ata
import net.alexandroid.adbugger.adbugger.generated.resources.clear_restart
import net.alexandroid.adbugger.adbugger.generated.resources.close
import net.alexandroid.adbugger.adbugger.generated.resources.day_mode
import net.alexandroid.adbugger.adbugger.generated.resources.enter
import net.alexandroid.adbugger.adbugger.generated.resources.home
import net.alexandroid.adbugger.adbugger.generated.resources.icon_128
import net.alexandroid.adbugger.adbugger.generated.resources.install
import net.alexandroid.adbugger.adbugger.generated.resources.night_mode
import net.alexandroid.adbugger.adbugger.generated.resources.open
import net.alexandroid.adbugger.adbugger.generated.resources.power
import net.alexandroid.adbugger.adbugger.generated.resources.recent
import net.alexandroid.adbugger.adbugger.generated.resources.restart
import net.alexandroid.adbugger.adbugger.generated.resources.settings
import net.alexandroid.adbugger.adbugger.generated.resources.snapshot
import net.alexandroid.adbugger.adbugger.generated.resources.tab
import net.alexandroid.adbugger.adbugger.generated.resources.tray_basic_controls
import net.alexandroid.adbugger.adbugger.generated.resources.tray_package_commands
import net.alexandroid.adbugger.adbugger.generated.resources.tray_quit
import net.alexandroid.adbugger.adbugger.generated.resources.tray_show_hide
import net.alexandroid.adbugger.adbugger.generated.resources.uninstall
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import store.AppStore

@Composable
fun ApplicationScope.ApplicationTray(state: WindowState, model: AppStore = koinInject()) {
    val trayState = rememberTrayState()

    val icon = painterResource(Res.drawable.icon_128)
    Tray(
        state = trayState,
        icon = icon,
        menu = {
            BasicControls(model)
            PackageCommands(model)
            Separator()
            Item(
                stringResource(Res.string.tray_show_hide),
                onClick = { state.isMinimized = !state.isMinimized })
            Separator()
            Item(stringResource(Res.string.tray_quit), onClick = ::exitApplication)
        }
    )
}

@Composable
private fun MenuScope.BasicControls(model: AppStore) {
    Menu(stringResource(Res.string.tray_basic_controls)) {
        Item(stringResource(Res.string.home), onClick = { model.onHomeClick() })
        Item(stringResource(Res.string.settings), onClick = { model.onSettingsClick() })
        Item(stringResource(Res.string.back), onClick = { model.onBackClick() })
        Item(stringResource(Res.string.recent), onClick = { model.onRecentClick() })
        Item(stringResource(Res.string.tab), onClick = { model.onTabClick() })
        Item(stringResource(Res.string.enter), onClick = { model.onEnterClick() })
        Item(stringResource(Res.string.power), onClick = { model.onPowerClick() })
        Item(stringResource(Res.string.day_mode), onClick = { model.onDayClick() })
        Item(stringResource(Res.string.night_mode), onClick = { model.onNightClick() })
        Item(stringResource(Res.string.snapshot), onClick = { model.onSnapClick() })
    }
}

@Composable
private fun MenuScope.PackageCommands(model: AppStore) {
    Menu(stringResource(Res.string.tray_package_commands)) {
        Item(stringResource(Res.string.open), onClick = { model.onHomeClick() })
        Item(stringResource(Res.string.close), onClick = { model.onHomeClick() })
        Item(stringResource(Res.string.restart), onClick = { model.onHomeClick() })
        Item(stringResource(Res.string.clear_ata), onClick = { model.onHomeClick() })
        Item(stringResource(Res.string.clear_restart), onClick = { model.onHomeClick() })
        Item(stringResource(Res.string.uninstall), onClick = { model.onHomeClick() })
        Item(stringResource(Res.string.install), onClick = { model.onHomeClick() })
    }
}
