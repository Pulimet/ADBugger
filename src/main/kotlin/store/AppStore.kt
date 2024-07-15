package store

import adb.Adb
import adb.Commands
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.DeviceInfo
import model.Package2
import ui.navigation.MenuItemId
import utils.KeysConverter

class AppStore {

    companion object {
        const val ALL_DEVICES = "All Devices / Emulators"
        const val PACKAGE_NONE = "Target not selected"
        const val EMULATOR_NONE = "Emulator not selected"
    }

    val version = "0.1.0"

    private val adb = Adb(::log)

    var state: AppState by mutableStateOf(initialState())
        private set

    // Public
    fun onLaunchedEffect(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            adb.startAdbInteract()
            getDevicesList(this)
        }
    }

    // Navigation
    fun showPage(menuItemId: MenuItemId) {
        setState { copy(menuItemSelected = menuItemId) }
    }

    // User Actions
    fun getDevicesList(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            setState { copy(isDevicesLoading = true, devicesList = emptyList(), selectedDevice = ALL_DEVICES) }
            val devicesList = adb.devicesInfo()
            delay(200)
            setState { copy(isDevicesLoading = false, devicesList = devicesList) }
        }
    }

    fun onDeviceClick(device: DeviceInfo) {
        setState { copy(selectedDevice = device.serial) }
    }

    fun onGetPackageListClick(coroutineScope: CoroutineScope) {
        if (state.selectedDevice == ALL_DEVICES) return
        coroutineScope.launch(Dispatchers.IO) {
            setState { copy(isPackagesLoading = true, packageList = emptyList(), selectedPackage = PACKAGE_NONE) }
            val packagesList = adb.packages(state.selectedDevice)
            delay(200)
            setState { copy(isPackagesLoading = false, packageList = packagesList) }
        }
    }

    fun onGetEmulatorsListClick(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            setState { copy(isEmulatorsLoading = true, emulatorsList = emptyList()) }
            val emulatorsList = adb.emulators()
            delay(200)
            setState { copy(isEmulatorsLoading = false, emulatorsList = emulatorsList) }
        }
    }


    fun onPackageClick(pckg: Package2) {
        setState { copy(selectedPackage = pckg.name) }
    }


    fun onOpenClick(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == PACKAGE_NONE) return
        coroutineScope.launch(Dispatchers.IO) {
            adb.openPackage(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onApkPath(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == PACKAGE_NONE) return
        coroutineScope.launch(Dispatchers.IO) {
            adb.getApkPath(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onCloseClick(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == PACKAGE_NONE) return
        coroutineScope.launch(Dispatchers.IO) {
            adb.closePackage(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onRestartClick(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == PACKAGE_NONE) return
        coroutineScope.launch(Dispatchers.IO) {
            adb.closePackage(state.selectedPackage, state.selectedDevice)
            delay(100)
            adb.openPackage(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onClearDataClick(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == PACKAGE_NONE) return
        coroutineScope.launch {
            adb.clearData(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onClearAndRestartClick(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == PACKAGE_NONE) return

        coroutineScope.launch(Dispatchers.IO) {
            adb.closePackage(state.selectedPackage, state.selectedDevice)
            adb.clearData(state.selectedPackage, state.selectedDevice)
            delay(100)
            adb.openPackage(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onUninstallClick(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == PACKAGE_NONE) return
        coroutineScope.launch {
            adb.uninstall(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onLaunchEmulatorClick(coroutineScope: CoroutineScope, emulatorName: String) {
        log("emulator -avd $emulatorName -netdelay none -netspeed full")
        coroutineScope.launch(Dispatchers.IO) {
            adb.launchEmulator(emulatorName)
        }
    }

    fun onWipeAndLaunch(coroutineScope: CoroutineScope, emulatorName: String) {
        log("emulator -avd $emulatorName -wipe-data")
        coroutineScope.launch(Dispatchers.IO) {
            adb.wipeAndLaunchEmulator(emulatorName)
        }
    }

    fun onKillEmulatorClick(coroutineScope: CoroutineScope, serial: String) {
        log(Commands.getKillEmulatorBySerial(serial))
        coroutineScope.launch(Dispatchers.IO) {
            adb.killEmulatorBySerial(serial)
        }
    }


    fun onKillAllEmulatorClick(coroutineScope: CoroutineScope) {
        log(Commands.getKillEmulatorBySerial("[SERIAL]"))
        coroutineScope.launch(Dispatchers.IO) {
            adb.killAllEmulators()
        }
    }


    fun onHomeClick(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            adb.showHome(state.selectedDevice)
        }
    }

    fun onSettingsClick(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            adb.showSettings(state.selectedDevice)
        }
    }

    fun onBackClick(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            adb.pressBack(state.selectedDevice)
        }
    }

    fun onTabClick(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            adb.pressTab(state.selectedDevice)
        }
    }

    fun onEnterClick(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            adb.pressEnter(state.selectedDevice)
        }
    }

    fun onPowerClick(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            adb.pressPower(state.selectedDevice)
        }
    }

    fun onSnapClick(coroutineScope: CoroutineScope) {
        if (state.selectedDevice == ALL_DEVICES) return
        coroutineScope.launch {
            adb.takeSnapshot(state.selectedDevice)
        }
    }

    fun onDayClick(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            adb.setDarkModeOff(state.selectedDevice)
        }
    }

    fun onNightClick(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            adb.setDarkModeOn(state.selectedDevice)
        }
    }

    fun onUpClick(coroutineScope: CoroutineScope) {
        log("adb shell " + Commands.getUp())
        coroutineScope.launch {
            adb.pressUp(state.selectedDevice)
        }
    }

    fun onDownClick(coroutineScope: CoroutineScope) {
        log("adb shell " + Commands.getDown())
        coroutineScope.launch {
            adb.pressDown(state.selectedDevice)
        }
    }

    fun onLeftClick(coroutineScope: CoroutineScope) {
        log("adb shell " + Commands.getLeft())
        coroutineScope.launch {
            adb.pressLeft(state.selectedDevice)
        }
    }

    fun onRightClick(coroutineScope: CoroutineScope) {
        log("adb shell " + Commands.getRight())
        coroutineScope.launch {
            adb.pressRight(state.selectedDevice)
        }
    }

    fun onBackSpaceClick(coroutineScope: CoroutineScope) {
        log("adb shell " + Commands.getDelete())
        coroutineScope.launch {
            adb.pressDelete(state.selectedDevice)
        }
    }

    fun onSendTextClick(coroutineScope: CoroutineScope, value: String) {
        log("adb shell " + Commands.sendTextCommand(value))
        coroutineScope.launch {
            adb.sendText(state.selectedDevice, value)
        }
    }

    fun onSendInputClick(coroutineScope: CoroutineScope, value: Int) {
        log("adb shell " + Commands.sendInputCommand(value))
        coroutineScope.launch {
            adb.sendInput(state.selectedDevice, value)
        }
    }

    fun onNumberClick(coroutineScope: CoroutineScope, i: Int) {
        log("adb shell " + Commands.sendInputCommand(i))
        coroutineScope.launch {
            adb.sendInputNum(state.selectedDevice, i)
        }
    }

    fun onLetterClick(coroutineScope: CoroutineScope, letter: String) {
        val key = KeysConverter.convertLetterToKeyCode(letter)
        log("adb shell " + Commands.sendInputCommand(key))
        coroutineScope.launch {
            adb.sendInput(state.selectedDevice, key)
        }
    }

    fun onForwardUserInputToggle(value: Boolean) {
        setState { copy(isUserForwardInputEnabled = value) }
    }

    fun onChangeAlwaysShowLog(alwaysShowLogsEnabled: Boolean) {
        setState { copy(isLogsAlwaysShown = alwaysShowLogsEnabled) }
    }

    fun onKeyEvent(coroutineScope: CoroutineScope, event: KeyEvent): Boolean {
        if (!state.isUserForwardInputEnabled || event.type != KeyEventType.KeyDown) {
            return false
        }
        val key: Int = KeysConverter.covertEventKeyToKeyCode(event)
        if (key != -1) {
            coroutineScope.launch {
                adb.sendInput(state.selectedDevice, key)
            }
            return true
        }
        val char = KeysConverter.convertEventKeyToChar(event)
        if (char.isNotEmpty()) {
            coroutineScope.launch {
                adb.sendText(state.selectedDevice, char)
            }
            return true
        }

        return false
    }


    fun onAdbReverse(coroutineScope: CoroutineScope, port: Int?) {
        if (port != null) {
            coroutineScope.launch {
                adb.reversePort(port)
            }
        }
    }

    fun onAdbReverseList(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            adb.reverseList()
        }
    }

    fun onAddPermission(coroutineScope: CoroutineScope, permission: String) {
        log(Commands.addSpecificPermission(state.selectedPackage, permission))
        coroutineScope.launch {
            adb.addPermission(state.selectedDevice, permission, state.selectedPackage)
        }
    }

    fun onRemovePermission(coroutineScope: CoroutineScope, permission: String) {
        log(Commands.revokeSpecificPermission(state.selectedPackage, permission))
        coroutineScope.launch {
            adb.removePermission(state.selectedDevice, permission, state.selectedPackage)
        }
    }

    fun onRemoveAllPermissions(coroutineScope: CoroutineScope) {
        log(Commands.getRevokeAllPermissions(state.selectedPackage))
        coroutineScope.launch {
            adb.removeAllPermissions(state.selectedDevice, state.selectedPackage)
        }
    }

    fun onGetPermissions(coroutineScope: CoroutineScope) {
        if (state.selectedDevice == ALL_DEVICES || state.selectedPackage == PACKAGE_NONE) return
        log("adb shell " + Commands.getGrantedPermissions(state.selectedPackage))
        coroutineScope.launch {
            adb.getPermissions(state.selectedDevice, state.selectedPackage)
        }
    }

    // Logs
    fun clearLogs() {
        setState { copy(logs = arrayListOf()) }
    }

    private fun log(log: String) {
        val newList = ArrayList(state.logs)
        newList.add(log)
        setState { copy(logs = newList) }
    }

    // Private
    private fun initialState() = AppState()

    private inline fun setState(update: AppState.() -> AppState) {
        state = state.update()
    }

    data class AppState(
        val menuItemSelected: MenuItemId = MenuItemId.DEVICES,
        val devicesList: List<DeviceInfo> = emptyList(),
        val selectedDevice: String = ALL_DEVICES,
        val packageList: List<Package2> = emptyList(),
        val emulatorsList: List<String> = emptyList(),
        val selectedPackage: String = PACKAGE_NONE,
        val isDevicesLoading: Boolean = false,
        val isPackagesLoading: Boolean = false,
        val isEmulatorsLoading: Boolean = false,
        val isUserForwardInputEnabled: Boolean = false,
        val isLogsAlwaysShown: Boolean = false,
        val logs: ArrayList<String> = arrayListOf()
    )
}
