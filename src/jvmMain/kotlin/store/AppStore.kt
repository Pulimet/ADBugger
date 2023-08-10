package store

import adb.Adb
import adb.Commands
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.*
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

    fun onSendInputClick(coroutineScope: CoroutineScope, value: Int) {
        log(Commands.sendInputCommand(value))
        coroutineScope.launch {
            adb.sendInput(state.selectedDevice, value)
        }
    }

    fun onNumberClick(coroutineScope: CoroutineScope, i: Int) {
        log(Commands.sendInputCommand(i))
        coroutineScope.launch {
            adb.sendInputNum(state.selectedDevice, i)
        }
    }

    fun onLetterClick(coroutineScope: CoroutineScope, letter: String) {
        val key = convertLetterToKeyCode(letter)
        log(Commands.sendInputCommand(key))
        coroutineScope.launch {
            adb.sendInput(state.selectedDevice, key)
        }
    }

    fun onKeyEvent(coroutineScope: CoroutineScope, event: KeyEvent): Boolean {
        if (event.type != KeyEventType.KeyDown) {
            return false
        }
        val key: Int = covertEventKeyToKeyCode(event)
        if (key != -1) {
            coroutineScope.launch {
                adb.sendInput(state.selectedDevice, key)
            }
            return true
        }
        val char = convertEventKeyToChar(event)
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
        coroutineScope.launch {
            adb.addPermission(state.selectedDevice, permission, state.selectedPackage)
        }
    }

    fun onRemovePermission(coroutineScope: CoroutineScope, permission: String) {
        coroutineScope.launch {
            adb.removePermission(state.selectedDevice, permission, state.selectedPackage)
        }
    }

    fun onRemoveAllPermissions(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            adb.removeAllPermissions(state.selectedDevice, state.selectedPackage)
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

    private fun convertEventKeyToChar(event: KeyEvent) = when (event.key) {
        Key.A -> if (event.isShiftPressed) "A" else "a"
        Key.B -> if (event.isShiftPressed) "B" else "b"
        Key.C -> if (event.isShiftPressed) "C" else "c"
        Key.D -> if (event.isShiftPressed) "D" else "d"
        Key.E -> if (event.isShiftPressed) "E" else "e"
        Key.F -> if (event.isShiftPressed) "F" else "f"
        Key.G -> if (event.isShiftPressed) "G" else "g"
        Key.H -> if (event.isShiftPressed) "H" else "h"
        Key.I -> if (event.isShiftPressed) "I" else "i"
        Key.J -> if (event.isShiftPressed) "J" else "j"
        Key.K -> if (event.isShiftPressed) "K" else "k"
        Key.L -> if (event.isShiftPressed) "L" else "l"
        Key.M -> if (event.isShiftPressed) "M" else "m"
        Key.N -> if (event.isShiftPressed) "N" else "n"
        Key.O -> if (event.isShiftPressed) "O" else "o"
        Key.P -> if (event.isShiftPressed) "P" else "p"
        Key.Q -> if (event.isShiftPressed) "Q" else "q"
        Key.R -> if (event.isShiftPressed) "R" else "r"
        Key.S -> if (event.isShiftPressed) "S" else "s"
        Key.T -> if (event.isShiftPressed) "T" else "t"
        Key.U -> if (event.isShiftPressed) "U" else "u"
        Key.V -> if (event.isShiftPressed) "V" else "v"
        Key.W -> if (event.isShiftPressed) "W" else "w"
        Key.X -> if (event.isShiftPressed) "X" else "x"
        Key.Y -> if (event.isShiftPressed) "Y" else "y"
        Key.Z -> if (event.isShiftPressed) "Z" else "z"
        Key.Zero -> if (event.isShiftPressed) ")" else "0"
        Key.One -> if (event.isShiftPressed) "!" else "1"
        Key.Two -> if (event.isShiftPressed) "@" else "2"
        Key.Three -> if (event.isShiftPressed) "\\#" else "3" //
        Key.Four -> if (event.isShiftPressed) "$" else "4"
        Key.Five -> if (event.isShiftPressed) "%" else "5"
        Key.Six -> if (event.isShiftPressed) "^" else "6"
        Key.Seven -> if (event.isShiftPressed) "\\&" else "7" //
        Key.Eight -> if (event.isShiftPressed) "\\*" else "8" //
        Key.Nine -> if (event.isShiftPressed) "\\(" else "9" //
        else -> {
            ""
        }
    }

    private fun covertEventKeyToKeyCode(event: KeyEvent) = when (event.key) {
//        Key.A -> convertLetterToKeyCode("A")
//        Key.B -> convertLetterToKeyCode("B")
//        Key.C -> convertLetterToKeyCode("C")
//        Key.D -> convertLetterToKeyCode("D")
//        Key.E -> convertLetterToKeyCode("E")
//        Key.F -> convertLetterToKeyCode("F")
//        Key.G -> convertLetterToKeyCode("G")
//        Key.H -> convertLetterToKeyCode("H")
//        Key.I -> convertLetterToKeyCode("I")
//        Key.J -> convertLetterToKeyCode("J")
//        Key.K -> convertLetterToKeyCode("K")
//        Key.L -> convertLetterToKeyCode("L")
//        Key.M -> convertLetterToKeyCode("M")
//        Key.N -> convertLetterToKeyCode("N")
//        Key.O -> convertLetterToKeyCode("O")
//        Key.P -> convertLetterToKeyCode("P")
//        Key.Q -> convertLetterToKeyCode("Q")
//        Key.R -> convertLetterToKeyCode("R")
//        Key.S -> convertLetterToKeyCode("S")
//        Key.T -> convertLetterToKeyCode("T")
//        Key.U -> convertLetterToKeyCode("U")
//        Key.V -> convertLetterToKeyCode("V")
//        Key.W -> convertLetterToKeyCode("W")
//        Key.X -> convertLetterToKeyCode("X")
//        Key.Y -> convertLetterToKeyCode("Y")
//        Key.Z -> convertLetterToKeyCode("Z")
//        Key.Zero -> 7
//        Key.One -> 8
//        Key.Two -> 9
//        Key.Three -> 10
//        Key.Four -> 11
//        Key.Five -> 12
//        Key.Six -> 13
//        Key.Seven -> 14
//        Key.Eight -> 15
//        Key.Nine -> 1
        Key.Backspace -> 67
        Key.Enter -> 66
        else -> -1
    }

    private fun convertLetterToKeyCode(letter: String) = when (letter) {
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
