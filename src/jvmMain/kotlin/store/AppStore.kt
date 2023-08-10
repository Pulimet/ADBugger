package store

import adb.Adb
import adb.Commands
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.malinskiy.adam.request.pkg.Package
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.DeviceInfo

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
        coroutineScope.launch(Dispatchers.IO) {
            adb.openPackage(state.selectedPackage, state.selectedDevice)
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
        coroutineScope.launch(Dispatchers.IO) {
            adb.launchEmulator(emulatorName)
        }
    }

    fun onWipeAndLaunch(coroutineScope: CoroutineScope, emulatorName: String) {
        coroutineScope.launch(Dispatchers.IO) {
            adb.wipeAndLaunchEmulator(emulatorName)
        }
    }

    fun onKillEmulatorClick(coroutineScope: CoroutineScope, serial: String) {
        coroutineScope.launch(Dispatchers.IO) {
            log(Commands.getKillEmulatorBySerial(serial))
            adb.killEmulatorBySerial(serial)
        }
    }


    fun onKillAllEmulatorClick(coroutineScope: CoroutineScope) {
        coroutineScope.launch(Dispatchers.IO) {
            adb.killAllEmulators()
        }
    }


    fun onHomeClick(coroutineScope: CoroutineScope) {
        log(Commands.getShowHome())
        coroutineScope.launch {
            adb.showHome(state.selectedDevice)
        }
    }

    fun onSettingsClick(coroutineScope: CoroutineScope) {
        log(Commands.getShowSettings())
        coroutineScope.launch {
            adb.showSettings(state.selectedDevice)
        }
    }

    fun onBackClick(coroutineScope: CoroutineScope) {
        log(Commands.getPressBack())
        coroutineScope.launch {
            adb.pressBack(state.selectedDevice)
        }
    }

    fun onTabClick(coroutineScope: CoroutineScope) {
        log(Commands.getPressTab())
        coroutineScope.launch {
            adb.pressTab(state.selectedDevice)
        }
    }

    fun onEnterClick(coroutineScope: CoroutineScope) {
        log(Commands.getPressEnter())
        coroutineScope.launch {
            adb.pressEnter(state.selectedDevice)
        }
    }

    fun onPowerClick(coroutineScope: CoroutineScope) {
        log(Commands.getPressPower())
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
        log(Commands.getDarkModeOff())
        coroutineScope.launch {
            adb.setDarkModeOff(state.selectedDevice)
        }
    }

    fun onNightClick(coroutineScope: CoroutineScope) {
        log(Commands.getDarkModeOn())
        coroutineScope.launch {
            adb.setDarkModeOn(state.selectedDevice)
        }
    }

    fun onUpClick(coroutineScope: CoroutineScope) {
        log(Commands.getUp())
        coroutineScope.launch {
            adb.pressUp(state.selectedDevice)
        }
    }

    fun onDownClick(coroutineScope: CoroutineScope) {
        log(Commands.getDown())
        coroutineScope.launch {
            adb.pressDown(state.selectedDevice)
        }
    }

    fun onLeftClick(coroutineScope: CoroutineScope) {
        log(Commands.getLeft())
        coroutineScope.launch {
            adb.pressLeft(state.selectedDevice)
        }
    }

    fun onRightClick(coroutineScope: CoroutineScope) {
        log(Commands.getRight())
        coroutineScope.launch {
            adb.pressRight(state.selectedDevice)
        }
    }

    fun onBackSpaceClick(coroutineScope: CoroutineScope) {
        log(Commands.getDelete())
        coroutineScope.launch {
            adb.pressDelete(state.selectedDevice)
        }
    }

    fun onSendTextClick(coroutineScope: CoroutineScope, value: String) {
        log(Commands.sendTextCommand(value))
        coroutineScope.launch {
            adb.sendText(state.selectedDevice, value)
        }
    }

    fun onSendInputClick(coroutineScope: CoroutineScope, value: String) {
        log(Commands.sendInputCommand(value))
        coroutineScope.launch {
            adb.sendInput(state.selectedDevice, value)
        }
    }

    fun onNumberClick(coroutineScope: CoroutineScope, i: Int) {
        log(Commands.sendInputCommand(i.toString()))
        coroutineScope.launch {
            adb.sendInputNum(state.selectedDevice, i)
        }
    }

    fun onLetterClick(coroutineScope: CoroutineScope, letter: String) {
        val key = when (letter) {
            "A" -> 29
            "B" -> 30
            "C" -> 31
            "D" -> 32
            "E" -> 33
            "F" -> 34
            "G" -> 35
            "H" -> 36
            "I" -> 37
            "J" -> 38
            "K" -> 39
            "L" -> 40
            "M" -> 41
            "N" -> 42
            "O" -> 43
            "P" -> 44
            "Q" -> 45
            "R" -> 46
            "S" -> 47
            "T" -> 48
            "U" -> 49
            "V" -> 50
            "W" -> 51
            "X" -> 52
            "Y" -> 53
            "Z" -> 54
            else -> 0
        }
        log(Commands.sendInputCommand(key.toString()))
        coroutineScope.launch {
            adb.sendInput(state.selectedDevice, key.toString())
        }
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
        val logs: ArrayList<String> = arrayListOf()
    )
}
