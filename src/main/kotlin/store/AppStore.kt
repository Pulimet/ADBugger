package store

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.Package
import model.TargetInfo
import pref.preference
import terminal.Terminal
import ui.navigation.sidebar.MenuItemId
import utils.KeysConverter
import kotlin.coroutines.CoroutineContext

class AppStore(private val terminal: Terminal, coroutineScope: CoroutineScope) : CoroutineScope {
    override val coroutineContext: CoroutineContext = coroutineScope.coroutineContext

    companion object {
        const val ALL_DEVICES = "All Devices / Emulators"
        const val PACKAGE_NONE = "Target not selected"
        const val EMULATOR_NONE = "Emulator not selected"
    }

    val version = "1.0.4"

    var state: AppState by mutableStateOf(initialState())
        private set

    private var logcatJob: Job? = null
    private var favoritePackagesPref: List<String> by preference("favoritePackages", emptyList())
    private fun convertToPackageList(list: List<String>) = list.map { Package(it) }


    // Callbacks
    private fun updateTargetsList(list: List<TargetInfo>) {
        setState { copy(targetsList = list) }
    }

    // Public
    fun onLaunchedEffect() {
        terminal.setupCallBacks(::log, ::updateTargetsList)

        launch { getDevicesList() }
        launch { getEmulatorsListClick() }
        launch { getEnvironmentVariables() }
        setState { copy(favoritePackages = convertToPackageList(favoritePackagesPref)) }
    }

    // Navigation
    fun showPage(menuItemId: MenuItemId) {
        setState { copy(menuItemSelected = menuItemId) }
    }

    // State checks
    fun isBottomLogsShown() = state.isLogsAlwaysShown && state.menuItemSelected != MenuItemId.LOGS


    // User Actions
    fun getDevicesList() {
        launch {
            setState { copy(isDevicesLoading = true, targetsList = emptyList(), selectedTargetsList = emptyList()) }
            terminal.devicesInfo()
            delay(200)
            setState { copy(isDevicesLoading = false) }
        }
    }


    fun onTargetClick(device: TargetInfo, isSelected: Boolean) {
        if (device.serial == ALL_DEVICES) {
            setState { copy(selectedTargetsList = emptyList()) }
            return
        }
        if (isSelected) {
            setState { copy(selectedTargetsList = selectedTargetsList + device.serial) }
        } else {
            setState { copy(selectedTargetsList = selectedTargetsList - device.serial) }
        }
    }

    fun onGetPackageListClick() {
        if (state.selectedTargetsList.isEmpty()) {
            log("Can't get packages. No target selected")
            return
        }
        if (state.selectedTargetsList.size > 1) {
            log("Multiple target selected. Getting packages from: ${state.selectedTargetsList[0]}")
        }
        launch {
            setState { copy(isPackagesLoading = true, packageList = emptyList(), selectedPackage = PACKAGE_NONE) }
            val packagesList = terminal.packages(state.selectedTargetsList[0])
            delay(200)
            setState { copy(isPackagesLoading = false, packageList = packagesList) }
        }
    }

    fun getEmulatorsListClick() {
        launch {
            setState { copy(isEmulatorsLoading = true, emulatorsList = emptyList()) }
            val emulatorsList = terminal.emulators()
            delay(200)
            setState { copy(isEmulatorsLoading = false, emulatorsList = emulatorsList) }
        }
    }

    fun onPackageClick(pckg: Package) {
        setState { copy(selectedPackage = pckg.name) }
    }

    fun onOpenClick() {
        if (state.selectedPackage == PACKAGE_NONE) return
        launch { terminal.openPackage(state.selectedPackage, state.selectedTargetsList) }
    }

    fun onApkPath() {
        if (state.selectedPackage == PACKAGE_NONE) return
        launch { terminal.getApkPath(state.selectedPackage, state.selectedTargetsList) }
    }

    fun onCloseClick() {
        if (state.selectedPackage == PACKAGE_NONE) return
        launch { terminal.closePackage(state.selectedPackage, state.selectedTargetsList) }
    }

    fun onRestartClick() {
        if (state.selectedPackage == PACKAGE_NONE) return
        launch {
            terminal.closePackage(state.selectedPackage, state.selectedTargetsList)
            delay(100)
            terminal.openPackage(state.selectedPackage, state.selectedTargetsList)
        }
    }

    fun onClearDataClick() {
        if (state.selectedPackage == PACKAGE_NONE) return
        launch { terminal.clearData(state.selectedPackage, state.selectedTargetsList) }
    }

    fun onClearAndRestartClick() {
        if (state.selectedPackage == PACKAGE_NONE) return
        launch {
            terminal.closePackage(state.selectedPackage, state.selectedTargetsList)
            terminal.clearData(state.selectedPackage, state.selectedTargetsList)
            delay(100)
            terminal.openPackage(state.selectedPackage, state.selectedTargetsList)
        }
    }

    fun onUninstallClick() {
        if (state.selectedPackage == PACKAGE_NONE) return

        launch { terminal.uninstall(state.selectedPackage, state.selectedTargetsList) }
    }


    fun onLaunchEmulatorClick(emulatorName: String) {
        launch { terminal.launchEmulator(emulatorName) }
    }

    fun onWipeAndLaunch(emulatorName: String) {
        launch { terminal.wipeAndLaunchEmulator(emulatorName) }
    }

    fun onKillEmulatorClick(serial: String) {
        launch { terminal.killEmulatorBySerial(serial) }
    }

    fun onKillAllEmulatorClick() {
        launch { terminal.killAllEmulators() }
    }

    fun onHomeClick() {
        launch { terminal.showHome(state.selectedTargetsList) }
    }

    fun onSettingsClick() {
        launch { terminal.showSettings(state.selectedTargetsList) }
    }

    fun onBackClick() {
        launch { terminal.pressBack(state.selectedTargetsList) }
    }

    fun onTabClick() {
        launch { terminal.pressTab(state.selectedTargetsList) }
    }

    fun onEnterClick() {
        launch { terminal.pressEnter(state.selectedTargetsList) }
    }

    fun onPowerClick() {
        launch { terminal.pressPower(state.selectedTargetsList) }
    }

    fun onSnapClick() {
        if (state.selectedTargetsList.isEmpty()) return

        launch { terminal.takeSnapshot(state.selectedTargetsList) }
    }

    fun onDayClick() {
        launch { terminal.setDarkModeOff(state.selectedTargetsList) }
    }

    fun onNightClick() {
        launch { terminal.setDarkModeOn(state.selectedTargetsList) }
    }

    fun onUpClick() {
        launch { terminal.pressUp(state.selectedTargetsList) }
    }

    fun onDownClick() {
        launch { terminal.pressDown(state.selectedTargetsList) }
    }

    fun onLeftClick() {
        launch { terminal.pressLeft(state.selectedTargetsList) }
    }

    fun onRightClick() {
        launch { terminal.pressRight(state.selectedTargetsList) }
    }

    fun onBackSpaceClick() {
        launch { terminal.pressDelete(state.selectedTargetsList) }
    }


    fun onSendTextClick(value: String) {
        launch { terminal.sendText(state.selectedTargetsList, value) }
    }


    fun onSendInputClick(value: Int) {
        launch { terminal.sendInput(state.selectedTargetsList, value) }
    }

    fun onNumberClick(i: Int) {
        launch { terminal.sendInputNum(state.selectedTargetsList, i) }
    }

    fun onLetterClick(letter: String) {
        val key = KeysConverter.convertLetterToKeyCode(letter)

        launch { terminal.sendInput(state.selectedTargetsList, key) }
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
            launch { terminal.sendInput(state.selectedTargetsList, key) }
            return true
        }
        val char = KeysConverter.convertEventKeyToChar(event)
        if (char.isNotEmpty()) {
            launch { terminal.sendText(state.selectedTargetsList, char) }
            return true
        }

        log("Ket not handled by a system: ${event.key}")

        return false
    }

    fun onAdbReverse(port: Int?) {
        if (port != null) {
            launch { terminal.reversePort(port) }
        }
    }

    fun onAdbReverseList() {
        launch { terminal.reverseList() }
    }

    fun onAddPermission(permission: String) {
        launch { terminal.addPermission(state.selectedTargetsList, permission, state.selectedPackage) }
    }

    fun onRemovePermission(permission: String) {
        launch { terminal.removePermission(state.selectedTargetsList, permission, state.selectedPackage) }
    }

    fun onRemoveAllPermissions() {
        launch { terminal.removeAllPermissions(state.selectedTargetsList, state.selectedPackage) }
    }

    fun onGetPermissions() {
        if (state.selectedTargetsList.isEmpty() || state.selectedPackage == PACKAGE_NONE) return
        launch { terminal.getPermissions(state.selectedTargetsList, state.selectedPackage) }
    }

    fun scaleFontTo(d: Double) {
        launch { terminal.changeFontSize(d, state.selectedTargetsList) }
    }

    fun setDensity(density: Int) {
        launch { terminal.changeDisplayDensity(density, state.selectedTargetsList) }
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
        launch { terminal.installApk(pathApk, state.selectedTargetsList) }
    }

    fun addPackageNameToFavorites(packageName: String) {
        favoritePackagesPref = favoritePackagesPref + packageName
        setState { copy(favoritePackages = convertToPackageList(favoritePackagesPref)) }
    }

    fun removePackageNamFromFavorites(packageName: String) {
        favoritePackagesPref = favoritePackagesPref - packageName
        setState { copy(favoritePackages = convertToPackageList(favoritePackagesPref)) }
    }

    // Logcat
    fun startStopLogcat(buffer: String, format: String, priorityLevel: String, tag: String) {
        if (state.isLogcatRunning) {
            stopLogcat()
            return
        }
        if (state.selectedTargetsList.size != 1) {
            log("Please select only one target to start logcat")
            return
        }
        setState { copy(isLogcatRunning = true) }
        logcatJob = launch {
            terminal.logcat(state.selectedTargetsList[0], buffer, format, priorityLevel, tag) { onNewLogcatLine(it) }
        }
    }

    private fun stopLogcat() {
        log("Stopping logcat")
        logcatJob?.cancel()
        setState { copy(isLogcatRunning = false) }
    }

    fun clearLocalLogcatLogs() {
        setState { copy(logcatLogs = arrayListOf()) }
    }

    private fun onNewLogcatLine(line: String) {
        val newList = ArrayList(state.logcatLogs)
        newList.add(line)
        setState { copy(logcatLogs = newList) }
    }

    fun clearTargetLogcatLogs() {
        if (state.selectedTargetsList.size != 1) {
            log("Please select only one target to clear logcat")
            return
        }
        launch { terminal.clearLogcat(state.selectedTargetsList[0]) }
    }

    fun saveLogcatLogsToDesktop() {
        if (state.selectedTargetsList.size != 1) {
            log("Please select only one target to save the logs")
            return
        }
        launch { terminal.saveLogcatToDesktop(state.selectedTargetsList[0]) }
    }

    fun saveBugreport() {
        if (state.selectedTargetsList.size != 1) {
            log("Please select only one target to save bugreport")
            return
        }
        launch { terminal.saveBugReport(state.selectedTargetsList[0]) }
    }

    private fun getEnvironmentVariables() {
        launch {
            val result: Map<String, String> = terminal.getEnvironmentVariables()
            setState { copy(environmentVariables = result) }
        }
    }

    // Logs
    fun clearAdbLogs() {
        setState { copy(adbLogs = listOf()) }
    }

    private fun log(log: String) {
        val newList = ArrayList(state.adbLogs)
        newList.add(log)
        setState { copy(adbLogs = newList) }
    }

    // Private
    private fun initialState() = AppState()

    private inline fun setState(update: AppState.() -> AppState) {
        state = state.update()
    }

    data class AppState(
        val menuItemSelected: MenuItemId = MenuItemId.DEVICES,
        val targetsList: List<TargetInfo> = emptyList(),
        val packageList: List<Package> = emptyList(),
        val selectedTargetsList: List<String> = emptyList(),
        val favoritePackages: List<Package> = emptyList(),
        val emulatorsList: List<String> = emptyList(),
        val selectedPackage: String = PACKAGE_NONE,
        val isDevicesLoading: Boolean = false,
        val isPackagesLoading: Boolean = false,
        val isEmulatorsLoading: Boolean = false,
        val isUserForwardInputEnabled: Boolean = false,
        val isLogsAlwaysShown: Boolean = false,
        val isFilePickerShown: Boolean = false,
        val adbLogs: List<String> = listOf(),
        val logcatLogs: List<String> = listOf(),
        val environmentVariables: Map<String, String> = mapOf(),
        val isLogcatRunning: Boolean = false
    )
}
