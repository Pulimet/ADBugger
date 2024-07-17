package store

import adb.Adb
import adb.Cmd
import adb.Commands
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type
import kotlinx.coroutines.delay
import model.DeviceInfo
import model.Package
import ui.navigation.sidebar.MenuItemId
import utils.KeysConverter

class AppStore(cmd: Cmd) {

    companion object {
        const val ALL_DEVICES = "All Devices / Emulators"
        const val PACKAGE_NONE = "Target not selected"
        const val EMULATOR_NONE = "Emulator not selected"
    }

    val version = "1.0.0"


    private val adb = Adb(cmd, ::log)

    var state: AppState by mutableStateOf(initialState())
        private set

    // Public
    suspend fun onLaunchedEffect() {
        getDevicesList()
    }

    // Navigation
    fun showPage(menuItemId: MenuItemId) {
        setState { copy(menuItemSelected = menuItemId) }
    }

    // User Actions
    suspend fun getDevicesList() {
        setState { copy(isDevicesLoading = true, devicesList = emptyList(), selectedDevice = ALL_DEVICES) }
        val devicesList = adb.devicesInfo()
        delay(200)
        setState { copy(isDevicesLoading = false, devicesList = devicesList) }
    }


    fun onDeviceClick(device: DeviceInfo) {
        setState { copy(selectedDevice = device.serial) }
    }

    suspend fun onGetPackageListClick() {
        if (state.selectedDevice == ALL_DEVICES) return
        setState { copy(isPackagesLoading = true, packageList = emptyList(), selectedPackage = PACKAGE_NONE) }
        val packagesList = adb.packages(state.selectedDevice)
        delay(200)
        setState { copy(isPackagesLoading = false, packageList = packagesList) }
    }

    suspend fun onGetEmulatorsListClick() {
        setState { copy(isEmulatorsLoading = true, emulatorsList = emptyList()) }
        val emulatorsList = adb.emulators()
        delay(200)
        setState { copy(isEmulatorsLoading = false, emulatorsList = emulatorsList) }
    }


    fun onPackageClick(pckg: Package) {
        setState { copy(selectedPackage = pckg.name) }
    }


    fun onOpenClick() {
        if (state.selectedPackage == PACKAGE_NONE) return
        adb.openPackage(state.selectedPackage, state.selectedDevice)
    }

    fun onApkPath() {
        if (state.selectedPackage == PACKAGE_NONE) return
        adb.getApkPath(state.selectedPackage, state.selectedDevice)
    }

    fun onCloseClick() {
        if (state.selectedPackage == PACKAGE_NONE) return
        adb.closePackage(state.selectedPackage, state.selectedDevice)
    }

    suspend fun onRestartClick() {
        if (state.selectedPackage == PACKAGE_NONE) return
        adb.closePackage(state.selectedPackage, state.selectedDevice)
        delay(100)
        adb.openPackage(state.selectedPackage, state.selectedDevice)
    }

    fun onClearDataClick() {
        if (state.selectedPackage == PACKAGE_NONE) return
        adb.clearData(state.selectedPackage, state.selectedDevice)
    }

    suspend fun onClearAndRestartClick() {
        if (state.selectedPackage == PACKAGE_NONE) return

        adb.closePackage(state.selectedPackage, state.selectedDevice)
        adb.clearData(state.selectedPackage, state.selectedDevice)
        delay(100)
        adb.openPackage(state.selectedPackage, state.selectedDevice)
    }

    fun onUninstallClick() {
        if (state.selectedPackage == PACKAGE_NONE) return

        adb.uninstall(state.selectedPackage, state.selectedDevice)
    }


    fun onLaunchEmulatorClick(emulatorName: String) {
        adb.launchEmulator(emulatorName)
    }

    fun onWipeAndLaunch(emulatorName: String) {
        adb.wipeAndLaunchEmulator(emulatorName)
    }

    fun onKillEmulatorClick(serial: String) {
        log(Commands.getKillEmulatorBySerial(serial))
        adb.killEmulatorBySerial(serial)
    }

    fun onKillAllEmulatorClick() {
        log(Commands.getKillEmulatorBySerial("[SERIAL]"))
        adb.killAllEmulators()
    }

    fun onHomeClick() {
        adb.showHome(state.selectedDevice)
    }

    fun onSettingsClick() {
        adb.showSettings(state.selectedDevice)
    }

    fun onBackClick() {
        adb.pressBack(state.selectedDevice)
    }

    fun onTabClick() {
        adb.pressTab(state.selectedDevice)
    }

    fun onEnterClick() {
        adb.pressEnter(state.selectedDevice)
    }

    fun onPowerClick() {
        adb.pressPower(state.selectedDevice)
    }

    fun onSnapClick() {
        if (state.selectedDevice == ALL_DEVICES) return

        adb.takeSnapshot(state.selectedDevice)
    }

    fun onDayClick() {
        adb.setDarkModeOff(state.selectedDevice)
    }

    fun onNightClick() {
        adb.setDarkModeOn(state.selectedDevice)
    }

    fun onUpClick() {
        adb.pressUp(state.selectedDevice)
    }

    fun onDownClick() {
        adb.pressDown(state.selectedDevice)
    }

    fun onLeftClick() {
        adb.pressLeft(state.selectedDevice)
    }

    fun onRightClick() {
        adb.pressRight(state.selectedDevice)
    }

    fun onBackSpaceClick() {
        adb.pressDelete(state.selectedDevice)
    }


    fun onSendTextClick(value: String) {
        adb.sendText(state.selectedDevice, value)
    }


    fun onSendInputClick(value: Int) {
        adb.sendInput(state.selectedDevice, value)
    }

    fun onNumberClick(i: Int) {
        adb.sendInputNum(state.selectedDevice, i)
    }

    fun onLetterClick(letter: String) {
        val key = KeysConverter.convertLetterToKeyCode(letter)

        adb.sendInput(state.selectedDevice, key)
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
            adb.sendInput(state.selectedDevice, key)
            return true
        }
        val char = KeysConverter.convertEventKeyToChar(event)
        if (char.isNotEmpty()) {
            adb.sendText(state.selectedDevice, char)
            return true
        }

        return false
    }

    fun onAdbReverse(port: Int?) {
        if (port != null) {
            adb.reversePort(port)
        }
    }

    fun onAdbReverseList() {
        adb.reverseList()
    }

    fun onAddPermission(permission: String) {
        adb.addPermission(state.selectedDevice, permission, state.selectedPackage)
    }

    fun onRemovePermission(permission: String) {
        adb.removePermission(state.selectedDevice, permission, state.selectedPackage)
    }

    fun onRemoveAllPermissions() {
        adb.removeAllPermissions(state.selectedDevice, state.selectedPackage)
    }

    fun onGetPermissions() {
        if (state.selectedDevice == ALL_DEVICES || state.selectedPackage == PACKAGE_NONE) return
        adb.getPermissions(state.selectedDevice, state.selectedPackage)
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
        val logs: ArrayList<String> = arrayListOf()
    )
}
