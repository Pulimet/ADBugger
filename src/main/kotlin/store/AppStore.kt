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
import model.ExtraType
import model.Extras
import model.Package
import model.RunEmulatorParams
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

    val version = "1.0.5"

    var state: AppState by mutableStateOf(initialState())
        private set

    private var logcatJob: Job? = null
    private var favoritePackagesPref: List<String> by preference("favoritePackages", emptyList())
    private var selectedTargetsPref: List<String> by preference("selectedTargets", emptyList())
    private var selectedPackagePref: String by preference("selectedPackage", "")
    private fun convertToPackageList(list: List<String>) = list.map { Package(it) }


    // Callbacks
    private fun updateTargetsList(list: List<TargetInfo>) {
        setState { copy(targetsList = list) }
    }

    // Public
    fun onLaunchedEffect() {
        terminal.setupCallBacks(::log, ::updateTargetsList)

        getDevicesList(true)
        launch { getEmulatorsListClick() }
        launch { getEnvironmentVariables() }
        setState { copy(favoritePackages = convertToPackageList(favoritePackagesPref)) }
        setState { copy(selectedPackage = selectedPackagePref) }
    }

    // State
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
        val isLogcatRunning: Boolean = false,
        val isAdbAccessOk: Status = Status.UNKNOWN,
        val isEmulatorAccessOk: Status = Status.UNKNOWN,
        val deviceProps: List<String> = listOf(),
        val permissions: List<String> = listOf(),
        val launchExtras: List<Extras> = listOf()
    )

    // Logs
    fun clearAdbLogs() {
        setState { copy(adbLogs = listOf()) }
    }

    private fun log(log: String) {
        val newList = ArrayList(state.adbLogs)
        newList.add(log)
        setState { copy(adbLogs = newList) }
    }

    // Navigation
    fun showPage(menuItemId: MenuItemId) {
        setState { copy(menuItemSelected = menuItemId) }
    }

    // State checks
    fun isBottomLogsShown() = state.isLogsAlwaysShown && state.menuItemSelected != MenuItemId.LOGS


    // User Actions
    fun onGetEnvironmentVariables() {
        launch {
            getEnvironmentVariables()
        }
    }

    fun getDevicesList(isLaunchedEffect: Boolean = false) = launch {
        setState {
            copy(
                isDevicesLoading = true,
                targetsList = emptyList(),
                selectedTargetsList = emptyList()
            )
        }
        terminal.devicesInfo()
        delay(200)
        setState { copy(isDevicesLoading = false) }

        addSelectedTargetsIfConnected(isLaunchedEffect)
    }

    private fun addSelectedTargetsIfConnected(isLaunchedEffect: Boolean) {
        if (isLaunchedEffect) {
            val newSelectedList = arrayListOf<String>()
            state.targetsList.forEach {
                if (selectedTargetsPref.contains(it.serial)) {
                    newSelectedList.add(it.serial)
                }
            }
            setState { copy(selectedTargetsList = newSelectedList) }
        }
    }


    fun onTargetClick(device: TargetInfo, isSelected: Boolean) {
        if (device.serial == ALL_DEVICES) {
            setState { copy(selectedTargetsList = emptyList()) }
            selectedTargetsPref = emptyList()
            return
        }
        if (isSelected) {
            setState { copy(selectedTargetsList = selectedTargetsList + device.serial) }
        } else {
            setState { copy(selectedTargetsList = selectedTargetsList - device.serial) }
        }
        selectedTargetsPref = state.selectedTargetsList
    }

    fun onGetPackageListClick(selectedOption: String) {
        if (state.selectedTargetsList.isEmpty()) {
            log("Can't get packages. No target selected")
            return
        }
        if (state.selectedTargetsList.size > 1) {
            log("Multiple target selected. Getting packages from: ${state.selectedTargetsList[0]}")
        }
        launch {
            setState {
                copy(
                    isPackagesLoading = true,
                    packageList = emptyList(),
                    selectedPackage = PACKAGE_NONE
                )
            }
            val packagesList = terminal.packages(state.selectedTargetsList[0], selectedOption)
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
        selectedPackagePref = pckg.name
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


    fun onLaunchEmulatorClick(emulatorName: String, params: RunEmulatorParams) {
        launch { terminal.launchEmulator(emulatorName, params) }
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

    fun onRecentClick() {
        launch { terminal.showRecentApps(state.selectedTargetsList) }
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

    fun onAdbReverse(portFrom: Int?, portTo: Int?) {
        if (portFrom != null && portTo != null) {
            launch { terminal.reversePort(portFrom, portTo) }
        }
    }

    fun onAdbForward(portFrom: Int?, portTo: Int?) {
        if (portFrom != null && portTo != null) {
            launch { terminal.forwardPort(portFrom, portTo) }
        }
    }

    fun onAdbReverseList() {
        launch { terminal.reverseList(state.selectedTargetsList) }
    }

    fun onAdbForwardList() {
        launch { terminal.forwardList(state.selectedTargetsList) }
    }

    fun onAdbReverseClear() {
        launch { terminal.reverseListClear(state.selectedTargetsList) }
    }

    fun onAdbForwardClear() {
        launch { terminal.forwardListClear(state.selectedTargetsList) }
    }

    fun onAddPermission(permission: String) {
        launch {
            terminal.addPermission(
                state.selectedTargetsList,
                permission,
                state.selectedPackage
            )
        }
    }

    fun onRemovePermission(permission: String) {
        launch {
            terminal.removePermission(
                state.selectedTargetsList,
                permission,
                state.selectedPackage
            )
        }
    }

    fun onRemoveAllPermissions() {
        launch { terminal.removeAllPermissions(state.selectedTargetsList, state.selectedPackage) }
    }

    fun onGetPermissions() {
        if (state.selectedTargetsList.size != 1) {
            log("Please select only one target to get it permissions")
            return
        }
        if (state.selectedPackage == PACKAGE_NONE) {
            log("Please select a package to get it permissions")
            return
        }
        launch {
            val permissions =
                terminal.getPermissions(state.selectedTargetsList, state.selectedPackage)
            setState { copy(permissions = permissions) }
        }
    }

    fun scaleFontTo(d: Double) {
        launch { terminal.changeFontSize(d, state.selectedTargetsList) }
    }

    fun setDensity(density: Int) {
        launch { terminal.changeDisplayDensity(density, state.selectedTargetsList) }
    }

    fun setSize(size: String) {
        launch { terminal.changeDisplaySize(size, state.selectedTargetsList) }
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
            terminal.logcat(
                state.selectedTargetsList[0],
                buffer,
                format,
                priorityLevel,
                tag
            ) { onNewLogcatLine(it) }
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

    private suspend fun getEnvironmentVariables() {
        val result: Map<String, String> = terminal.getEnvironmentVariables()
        setState { copy(environmentVariables = result) }
    }

    fun setProxy(proxyText: String) {
        launch { terminal.setProxy(proxyText, state.selectedTargetsList) }
    }

    fun removeProxy() {
        launch { terminal.removeProxy(state.selectedTargetsList) }
    }

    fun getProxy() {
        launch { terminal.getProxy(state.selectedTargetsList) }
    }

    fun onAirplaneOn() {
        launch { terminal.onAirplaneOn(state.selectedTargetsList) }
    }

    fun onAirplaneOff() {
        launch { terminal.onAirplaneOff(state.selectedTargetsList) }
    }

    fun onWifiOn() {
        launch { terminal.onWifiOn(state.selectedTargetsList) }
    }

    fun onWifiOff() {
        launch { terminal.onWifiOff(state.selectedTargetsList) }
    }

    fun onGpsOn() {
        launch { terminal.onGpsOn(state.selectedTargetsList) }
    }

    fun onGpsOff() {
        launch { terminal.onGpsOff(state.selectedTargetsList) }
    }

    fun onRotationAutoTurnOn() {
        launch { terminal.onRotationAutoTurnOn(state.selectedTargetsList) }
    }

    fun onRotationAutoTurnOff() {
        launch { terminal.onRotationAutoTurnOff(state.selectedTargetsList) }
    }

    fun onRotationLandscape() {
        launch { terminal.onRotationLandscape(state.selectedTargetsList) }
    }

    fun onRotationPortrait() {
        launch { terminal.onRotationPortrait(state.selectedTargetsList) }
    }

    fun onRotationPortraitUpSideDown() {
        launch { terminal.onRotationPortraitUpSideDown(state.selectedTargetsList) }
    }

    fun onRotationLandscapeUpSideDow() {
        launch { terminal.onRotationLandscapeUpSideDow(state.selectedTargetsList) }
    }

    fun onGetDeviceProps() {
        if (state.selectedTargetsList.size != 1) {
            log("Please select only one target to get device properties")
            return
        }
        launch {
            val props = terminal.getDeviceProps(state.selectedTargetsList[0])
            setState { copy(deviceProps = props) }
        }
    }

    fun checkPlatformTools() {
        launch {
            val result = terminal.checkPlatformTools()
            val isAdbAccessOk =
                result.isNotEmpty() && result[0].contains("Android Debug Bridge version")
            setState { copy(isAdbAccessOk = if (isAdbAccessOk) Status.OK else Status.FAIL) }
        }
    }

    fun checkEmulators() {
        launch {
            val result = terminal.checkEmulators()
            val isEmulatorAccessOk =
                result.isNotEmpty() && result[0].contains("Android emulator version")
            setState { copy(isEmulatorAccessOk = if (isEmulatorAccessOk) Status.OK else Status.FAIL) }
        }
    }

    fun testLaunch(selectedActivity: String) {
        if (state.selectedPackage == PACKAGE_NONE) {
            log("Please select Target and Package before")
            return
        }
        launch {
            terminal.testLaunch(
                state.selectedTargetsList,
                selectedActivity,
                state.launchExtras
            )
        }
    }

    fun onGetPackageActivities() {
        if (state.selectedPackage == PACKAGE_NONE) {
            log("Please select Target and Package before")
            return
        }
        launch { terminal.getPackageActivities(state.selectedTargetsList, state.selectedPackage) }
    }

    fun addExtra(key: String, value: String, type: String) {
        setState {
            copy(
                launchExtras = launchExtras.toMutableList()
                    .also { it.add(Extras(key, value, ExtraType.fromName(type))) }
            )
        }
    }

    fun onExtraDeleteClick(key: String) {
        setState {
            copy(launchExtras = state.launchExtras.filter {
                it.key != key
            })
        }
    }

    fun onStartServer() {
        launch { terminal.startAdbServer() }
    }

    fun onKillServer() {
        launch { terminal.killAdbServer() }
    }

    fun onAdbUsb() {
        launch { terminal.adbUsb() }
    }

    fun onAdbTcpIp(port: String) {
        launch { terminal.adbTcpIp(port) }
    }

    fun onDisconnectAll() {
        launch {  terminal.disconnectAll() }
    }

    fun onAdbConnect(ip: String, port: String) {
        launch { terminal.adbConnect(ip, port) }
    }

    fun onAdbDisconnect(ip: String, port: String) {
        launch { terminal.adbDisconnect(ip, port) }
    }

}
