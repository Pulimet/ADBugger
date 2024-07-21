package store

import adb.Adb
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.DeviceInfo
import model.Package
import ui.navigation.sidebar.MenuItemId
import utils.KeysConverter
import kotlin.coroutines.CoroutineContext

class AppStore(private val adb: Adb, coroutineScope: CoroutineScope) : CoroutineScope {
    override val coroutineContext: CoroutineContext = coroutineScope.coroutineContext

    companion object {
        const val ALL_DEVICES = "All Devices / Emulators"
        const val PACKAGE_NONE = "Target not selected"
        const val EMULATOR_NONE = "Emulator not selected"
    }

    val version = "1.0.1"

    var state: AppState by mutableStateOf(initialState())
        private set

    private fun updateDevicesList(list: List<DeviceInfo>) {
        setState { copy(devicesList = list) }
    }

    // Public
    fun onLaunchedEffect() {
        adb.setupCallBacks(::log, ::updateDevicesList)

        launch { getDevicesList() }
        launch { getEmulatorsListClick() }
    }

    // Navigation
    fun showPage(menuItemId: MenuItemId) {
        setState { copy(menuItemSelected = menuItemId) }
    }

    // User Actions
    fun getDevicesList() {
        launch {
            setState { copy(isDevicesLoading = true, devicesList = emptyList(), selectedDevice = ALL_DEVICES) }
            adb.devicesInfo()
            delay(200)
            setState { copy(isDevicesLoading = false) }
        }
    }


    fun onDeviceClick(device: DeviceInfo) {
        setState { copy(selectedDevice = device.serial) }
    }

    fun onGetPackageListClick() {
        if (state.selectedDevice == ALL_DEVICES) return
        launch {
            setState { copy(isPackagesLoading = true, packageList = emptyList(), selectedPackage = PACKAGE_NONE) }
            val packagesList = adb.packages(state.selectedDevice)
            delay(200)
            setState { copy(isPackagesLoading = false, packageList = packagesList) }
        }
    }

    fun getEmulatorsListClick() {
        launch {
            setState { copy(isEmulatorsLoading = true, emulatorsList = emptyList()) }
            val emulatorsList = adb.emulators()
            delay(200)
            setState { copy(isEmulatorsLoading = false, emulatorsList = emulatorsList) }
        }
    }


    fun onPackageClick(pckg: Package) {
        setState { copy(selectedPackage = pckg.name) }
    }


    fun onOpenClick() {
        if (state.selectedPackage == PACKAGE_NONE) return
        launch { adb.openPackage(state.selectedPackage, state.selectedDevice) }
    }

    fun onApkPath() {
        if (state.selectedPackage == PACKAGE_NONE) return
        launch { adb.getApkPath(state.selectedPackage, state.selectedDevice) }
    }

    fun onCloseClick() {
        if (state.selectedPackage == PACKAGE_NONE) return
        launch { adb.closePackage(state.selectedPackage, state.selectedDevice) }
    }

    fun onRestartClick() {
        if (state.selectedPackage == PACKAGE_NONE) return
        launch {
            adb.closePackage(state.selectedPackage, state.selectedDevice)
            delay(100)
            adb.openPackage(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onClearDataClick() {
        if (state.selectedPackage == PACKAGE_NONE) return
        launch { adb.clearData(state.selectedPackage, state.selectedDevice) }
    }

    fun onClearAndRestartClick() {
        if (state.selectedPackage == PACKAGE_NONE) return
        launch {
            adb.closePackage(state.selectedPackage, state.selectedDevice)
            adb.clearData(state.selectedPackage, state.selectedDevice)
            delay(100)
            adb.openPackage(state.selectedPackage, state.selectedDevice)
        }
    }

    fun onUninstallClick() {
        if (state.selectedPackage == PACKAGE_NONE) return

        launch { adb.uninstall(state.selectedPackage, state.selectedDevice) }
    }


    fun onLaunchEmulatorClick(emulatorName: String) {
        launch { adb.launchEmulator(emulatorName) }
    }

    fun onWipeAndLaunch(emulatorName: String) {
        launch { adb.wipeAndLaunchEmulator(emulatorName) }
    }

    fun onKillEmulatorClick(serial: String) {
        launch { adb.killEmulatorBySerial(serial) }
    }

    fun onKillAllEmulatorClick() {
        launch { adb.killAllEmulators() }
    }

    fun onHomeClick() {
        launch { adb.showHome(state.selectedDevice) }
    }

    fun onSettingsClick() {
        launch { adb.showSettings(state.selectedDevice) }
    }

    fun onBackClick() {
        launch { adb.pressBack(state.selectedDevice) }
    }

    fun onTabClick() {
        launch { adb.pressTab(state.selectedDevice) }
    }

    fun onEnterClick() {
        launch { adb.pressEnter(state.selectedDevice) }
    }

    fun onPowerClick() {
        launch { adb.pressPower(state.selectedDevice) }
    }

    fun onSnapClick() {
        if (state.selectedDevice == ALL_DEVICES) return

        launch { adb.takeSnapshot(state.selectedDevice) }
    }

    fun onDayClick() {
        launch { adb.setDarkModeOff(state.selectedDevice) }
    }

    fun onNightClick() {
        launch { adb.setDarkModeOn(state.selectedDevice) }
    }

    fun onUpClick() {
        launch { adb.pressUp(state.selectedDevice) }
    }

    fun onDownClick() {
        launch { adb.pressDown(state.selectedDevice) }
    }

    fun onLeftClick() {
        launch { adb.pressLeft(state.selectedDevice) }
    }

    fun onRightClick() {
        launch { adb.pressRight(state.selectedDevice) }
    }

    fun onBackSpaceClick() {
        launch { adb.pressDelete(state.selectedDevice) }
    }


    fun onSendTextClick(value: String) {
        launch { adb.sendText(state.selectedDevice, value) }
    }


    fun onSendInputClick(value: Int) {
        launch { adb.sendInput(state.selectedDevice, value) }
    }

    fun onNumberClick(i: Int) {
        launch { adb.sendInputNum(state.selectedDevice, i) }
    }

    fun onLetterClick(letter: String) {
        val key = KeysConverter.convertLetterToKeyCode(letter)

        launch { adb.sendInput(state.selectedDevice, key) }
    }

    fun onForwardUserInputToggle(value: Boolean) {
        setState { copy(isUserForwardInputEnabled = value) }
    }

    fun onChangeAlwaysShowLog(alwaysShowLogsEnabled: Boolean) {
        setState { copy(isLogsAlwaysShown = alwaysShowLogsEnabled) }
    }

    fun onKeyEvent(event: KeyEvent): Boolean {
        if (!state.isUserForwardInputEnabled || event.type != KeyEventType.KeyDown) {
            return false
        }
        val key: Int = KeysConverter.covertEventKeyToKeyCode(event)
        if (key != -1) {
            launch { adb.sendInput(state.selectedDevice, key) }
            return true
        }
        val char = KeysConverter.convertEventKeyToChar(event)
        if (char.isNotEmpty()) {
            launch { adb.sendText(state.selectedDevice, char) }
            return true
        }

        log("Ket not handled by a system: ${event.key}")

        return false
    }

    fun onAdbReverse(port: Int?) {
        if (port != null) {
            launch { adb.reversePort(port) }
        }
    }

    fun onAdbReverseList() {
        launch { adb.reverseList() }
    }

    fun onAddPermission(permission: String) {
        launch { adb.addPermission(state.selectedDevice, permission, state.selectedPackage) }
    }

    fun onRemovePermission(permission: String) {
        launch { adb.removePermission(state.selectedDevice, permission, state.selectedPackage) }
    }

    fun onRemoveAllPermissions() {
        launch { adb.removeAllPermissions(state.selectedDevice, state.selectedPackage) }
    }

    fun onGetPermissions() {
        if (state.selectedDevice == ALL_DEVICES || state.selectedPackage == PACKAGE_NONE) return
        launch { adb.getPermissions(state.selectedDevice, state.selectedPackage) }
    }

    fun scaleFontTo(d: Double) {
        launch { adb.changeFontSize(d, state.selectedDevice) }
    }

    fun setDensity(density: Int) {
        launch { adb.changeDisplayDensity(density, state.selectedDevice) }
    }

    fun openFilePicker() {
        setState { copy(isFilePickerShown = true) }
    }

    fun onFilePickerResult(dir: String?, file: String?) {
        setState { copy(isFilePickerShown = false) }
        if (dir.isNullOrEmpty() || file.isNullOrEmpty()) {
            log("File not picked")
            return
        }
        val pathApk = dir + file
        log("File picked: $pathApk")
        launch { adb.installApk(pathApk, state.selectedDevice) }
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
        val packageList: List<Package> = emptyList(),
        val emulatorsList: List<String> = emptyList(),
        val selectedPackage: String = PACKAGE_NONE,
        val isDevicesLoading: Boolean = false,
        val isPackagesLoading: Boolean = false,
        val isEmulatorsLoading: Boolean = false,
        val isUserForwardInputEnabled: Boolean = false,
        val isLogsAlwaysShown: Boolean = false,
        val isFilePickerShown: Boolean = false,
        val logs: ArrayList<String> = arrayListOf()
    )
}
