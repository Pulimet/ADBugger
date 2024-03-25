package store

import adb.Adb
import adb.Commands
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type
import com.malinskiy.adam.request.pkg.Package
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.DeviceInfo
import ui.navigation.MenuItemId
import utils.KeysConverter

class AppStore {

    companion object {
        const val ALL_DEVICES = "All Devices"
        const val PACKAGE_NONE = "Package not selected"
        const val EMULATOR_NONE = "Emulator not selected"
    }

    private val adb = Adb()
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
    }


    // User Actions
    fun getDevicesList(coroutineScope: CoroutineScope) {
        log("adb devices")
        coroutineScope.launch(Dispatchers.IO) {
            setState { copy(isDevicesLoading = true, devicesList = emptyList(), selectedDevice = ALL_DEVICES) }
            delay(500)
            setState { copy(isDevicesLoading = false, devicesList = adb.devicesInfo()) }
        }
    }

    fun onDeviceClick(device: DeviceInfo) {
        setState { copy(selectedDevice = device.serial) }
    }

    fun onGetPackageListClick(coroutineScope: CoroutineScope) {
        if (state.selectedDevice == ALL_DEVICES) return
        log("adb shell pm list packages")
        coroutineScope.launch(Dispatchers.IO) {
            setState { copy(isPackagesLoading = true, packageList = emptyList(), selectedPackage = PACKAGE_NONE) }
            delay(500)
            setState { copy(isPackagesLoading = false, packageList = adb.packages(state.selectedDevice)) }
        }
    }

    fun onGetEmulatorsListClick(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            setState { copy(isEmulatorsLoading = true, emulatorsList = emptyList()) }
            val emulatorsList = adb.emulators(::log)
            delay(500)
            setState { copy(isEmulatorsLoading = false, emulatorsList = emulatorsList) }
        }
    }


    fun onPackageClick(pckg: Package) {
        setState { copy(selectedPackage = pckg.name) }
    }


    fun onOpenClick(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == PACKAGE_NONE) return
        log("adb shell " + Commands.getLaunchCommand(state.selectedPackage))
        coroutineScope.launch(Dispatchers.IO) {
            adb.openPackage(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onApkPath(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == PACKAGE_NONE) return
        coroutineScope.launch(Dispatchers.IO) {
            adb.getApkPath(state.selectedPackage, state.selectedDevice, ::log)
        }
    }

    fun onCloseClick(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == PACKAGE_NONE) return
        log("adb shell " + Commands.getCloseCommand(state.selectedPackage))
        coroutineScope.launch(Dispatchers.IO) {
            adb.closePackage(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onRestartClick(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == PACKAGE_NONE) return
        log("adb shell " + Commands.getLaunchCommand(state.selectedPackage))
        log("adb shell " + Commands.getCloseCommand(state.selectedPackage))
        coroutineScope.launch(Dispatchers.IO) {
            adb.closePackage(state.selectedPackage, state.selectedDevice)
            delay(100)
            adb.openPackage(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onClearDataClick(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == PACKAGE_NONE) return
        log("adb shell " + Commands.getClearDataCommand(state.selectedPackage))
        coroutineScope.launch {
            adb.clearData(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onClearAndRestartClick(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == PACKAGE_NONE) return

        log("adb shell " + Commands.getCloseCommand(state.selectedPackage))
        log("adb shell " + Commands.getClearDataCommand(state.selectedPackage))
        log("adb shell " + Commands.getLaunchCommand(state.selectedPackage))
        coroutineScope.launch(Dispatchers.IO) {
            adb.closePackage(state.selectedPackage, state.selectedDevice)
            adb.clearData(state.selectedPackage, state.selectedDevice)
            delay(100)
            adb.openPackage(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onUninstallClick(coroutineScope: CoroutineScope) {
        if (state.selectedPackage == PACKAGE_NONE) return
        log("adb shell " + Commands.getUninstallCommand(state.selectedPackage))
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
        log("adb shell " + Commands.getShowHome())
        coroutineScope.launch {
            adb.showHome(state.selectedDevice)
        }
    }

    fun onSettingsClick(coroutineScope: CoroutineScope) {
        log("adb shell " + Commands.getShowSettings())
        coroutineScope.launch {
            adb.showSettings(state.selectedDevice)
        }
    }

    fun onBackClick(coroutineScope: CoroutineScope) {
        log("adb shell " + Commands.getPressBack())
        coroutineScope.launch {
            adb.pressBack(state.selectedDevice)
        }
    }

    fun onTabClick(coroutineScope: CoroutineScope) {
        log("adb shell " + Commands.getPressTab())
        coroutineScope.launch {
            adb.pressTab(state.selectedDevice)
        }
    }

    fun onEnterClick(coroutineScope: CoroutineScope) {
        log("adb shell " + Commands.getPressEnter())
        coroutineScope.launch {
            adb.pressEnter(state.selectedDevice)
        }
    }

    fun onPowerClick(coroutineScope: CoroutineScope) {
        log("adb shell " + Commands.getPressPower())
        coroutineScope.launch {
            adb.pressPower(state.selectedDevice)
        }
    }

    fun onSnapClick(coroutineScope: CoroutineScope) {
        if (state.selectedDevice == ALL_DEVICES) return

        val filename = "snap_${state.selectedDevice}.png"
        log("adb shell screencap -p /sdcard/$filename")
        log("adb -s ${state.selectedDevice} pull /sdcard/$filename ~/Desktop/$filename")
        log("adb shell rm /sdcard/$filename")

        coroutineScope.launch {
            adb.takeSnapshot(state.selectedDevice)
        }
    }

    fun onDayClick(coroutineScope: CoroutineScope) {
        log("adb shell " + Commands.getDarkModeOff())
        coroutineScope.launch {
            adb.setDarkModeOff(state.selectedDevice)
        }
    }

    fun onNightClick(coroutineScope: CoroutineScope) {
        log("adb shell " + Commands.getDarkModeOn())
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

    fun onKeyboardInputToggle(value: Boolean) {
        setState { copy(isKeysInputEnabled = value) }
    }

    fun onPortForwardingToggle(value: Boolean) {
        setState { copy(isPortForwardingShown = value) }
    }

    fun onLogsToggle(value: Boolean) {
        setState { copy(isLogsShown = value) }
    }

    fun onDevicesControlToggle(value: Boolean) {
        setState { copy(isDevicesControlsShown = value) }
    }

    fun onForwardUserInputToggle(value: Boolean) {
        setState { copy(isUserForwardInputEnabled = value) }
    }

    fun onWorkingWithPackageToggle(value: Boolean) {
        setState { copy(isWorkingWithPackageShown = value) }
    }

    fun onDevicesCommandsToggle(value: Boolean) {
        setState { copy(isDevicesCommandsShown = value) }
    }

    fun onKeyEvent(coroutineScope: CoroutineScope, event: KeyEvent): Boolean {
        if (!state.isUserForwardInputEnabled || event.type != KeyEventType.KeyDown) {
            return false
        }
        val key: Int = KeysConverter.covertEventKeyToKeyCode(event)
        if (key != -1) {
            log("adb shell " + Commands.sendInputCommand(key))
            coroutineScope.launch {
                adb.sendInput(state.selectedDevice, key)
            }
            return true
        }
        val char = KeysConverter.convertEventKeyToChar(event)
        if (char.isNotEmpty()) {
            log("adb shell " + Commands.sendTextCommand(char))
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
                adb.reversePort(port, ::log)
            }
        }
    }

    fun onAdbReverseList(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            adb.reverseList(::log)
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
            adb.getPermissions(state.selectedDevice, state.selectedPackage, ::log)
        }
    }

    // Logs
    fun clearLogs() {
        setState { copy(logs = arrayListOf()) }
    }

    fun log(log: String) {
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
        val devicesList: List<DeviceInfo> = emptyList(),
        val selectedDevice: String = ALL_DEVICES,
        val packageList: List<Package> = emptyList(),
        val emulatorsList: List<String> = emptyList(),
        val selectedPackage: String = PACKAGE_NONE,
        val isDevicesLoading: Boolean = false,
        val isPackagesLoading: Boolean = false,
        val isEmulatorsLoading: Boolean = false,
        val isKeysInputEnabled: Boolean = false,
        val isPortForwardingShown: Boolean = false,
        val isLogsShown: Boolean = false,
        val isDevicesControlsShown: Boolean = false,
        val isUserForwardInputEnabled: Boolean = false,
        val isWorkingWithPackageShown: Boolean = false,
        val isDevicesCommandsShown: Boolean = false,
        val logs: ArrayList<String> = arrayListOf()
    )
}
