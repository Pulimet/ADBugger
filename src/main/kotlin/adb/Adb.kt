package adb

import model.DeviceInfo
import model.Package
import store.AppStore

class Adb(private val cmd: Cmd, private val log: (String) -> Unit) {

    // Public
    fun devicesInfo() =
        cmd.execute(Commands.getDeviceList(), log, Commands.getPlatformToolsPath()).drop(1).dropLast(1).map {
            // For case I need it later
            // val avdName = launchShellCommand(it.serial, "getprop ro.boot.qemu.avd_name").stdout.toString().trim()
            val serialAndType = it.split("\\s+".toRegex())
            DeviceInfo(serialAndType[0], serialAndType[1])
        }

    fun packages(serial: String): ArrayList<Package> {
        return launchShellCommand(serial, Commands.getPackageList())
            .map { Package(it.split(":").last()) }
            .filter { it.name.isNotEmpty() } as ArrayList<Package>
    }

    fun emulators(): List<String> {
        return cmd.execute(Commands.getEmulatorList(), log, Commands.getEmulatorPath())
    }

    fun openPackage(selectedPackage: String, selectedDevice: String) {
        launchShell(selectedDevice, Commands.getLaunchCommand(selectedPackage))
    }

    fun getApkPath(selectedPackage: String, selectedDevice: String) {
        val result = cmd.execute(
            Commands.getApkPathCommand(selectedPackage, selectedDevice),
            log,
            Commands.getPlatformToolsPath()
        )
        result.forEach {
            log(it)
        }
    }

    fun closePackage(selectedPackage: String, selectedDevice: String) {
        launchShell(selectedDevice, Commands.getCloseCommand(selectedPackage))
    }


    fun clearData(selectedPackage: String, selectedDevice: String) {
        launchShell(selectedDevice, Commands.getClearDataCommand(selectedPackage))
    }

    fun uninstall(selectedPackage: String, selectedDevice: String) {
        launchShell(selectedDevice, Commands.getUninstallCommand(selectedPackage))
    }

    fun killAllEmulators() {
        devicesInfo().forEach {
            killEmulatorBySerial(it.serial)
        }
    }

    fun killEmulatorBySerial(serial: String) {
        cmd.execute(Commands.getKillEmulatorBySerial(serial), path = Commands.getPlatformToolsPath())
    }

    fun launchEmulator(emulatorName: String) {
        cmd.execute(Commands.getLaunchEmulator(emulatorName), path = Commands.getEmulatorPath())
    }

    fun wipeAndLaunchEmulator(emulatorName: String) {
        cmd.execute(Commands.getWipeDataEmulatorByName(emulatorName), path = Commands.getEmulatorPath())
    }

    fun showHome(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getShowHome())
    }

    fun showSettings(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getShowSettings())
    }

    fun pressBack(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getPressBack())
    }

    fun pressTab(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getPressTab())
    }

    fun pressEnter(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getPressEnter())
    }

    fun pressPower(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getPressPower())
    }

    fun takeSnapshot(selectedDevice: String) {
        val filename = "snap_$selectedDevice.png"
        launchShellCommand(selectedDevice, "screencap -p /sdcard/$filename")
        val pullCommand = "adb -s $selectedDevice pull /sdcard/$filename ~/Desktop/$filename"
        log(pullCommand)
        cmd.execute(
            pullCommand,
            path = Commands.getPlatformToolsPath()
        )
        launchShellCommand(selectedDevice, "rm /sdcard/$filename")
    }


    fun setDarkModeOff(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getDarkModeOff())
    }

    fun setDarkModeOn(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getDarkModeOn())
    }

    fun pressUp(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getUp())
    }

    fun pressDown(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getDown())
    }

    fun pressLeft(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getLeft())
    }

    fun pressRight(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getRight())
    }

    fun pressDelete(selectedDevice: String) {
        launchShell(selectedDevice, Commands.getDelete())
    }

    fun sendText(selectedDevice: String, value: String) {
        launchShell(selectedDevice, Commands.sendTextCommand(value))
    }

    fun sendInput(selectedDevice: String, value: Int) {
        launchShell(selectedDevice, Commands.sendInputCommand(value))
    }

    fun sendInputNum(selectedDevice: String, num: Int) {
        launchShell(selectedDevice, Commands.sendInputCommand((num + 7)))
    }

    fun reversePort(port: Int) {
        devicesInfo().forEach { device ->
            val resultList = cmd.execute(Commands.adbReverse(device.serial, port), log, Commands.getPlatformToolsPath())
            resultList.forEach {
                log(it)
            }
        }
    }

    fun reverseList() {
        val resultList = cmd.execute(Commands.adbReverseList(), log, Commands.getPlatformToolsPath())
        resultList.forEach {
            log(it)
        }
    }

    fun removeAllPermissions(selectedDevice: String, selectedPackage: String) {
        launchShell(selectedDevice, Commands.getRevokeAllPermissions(selectedPackage))
    }

    fun addPermission(selectedDevice: String, permission: String, selectedPackage: String) {
        launchShell(selectedDevice, Commands.addSpecificPermission(selectedPackage, permission))
    }

    fun removePermission(selectedDevice: String, permission: String, selectedPackage: String) {
        launchShell(selectedDevice, Commands.revokeSpecificPermission(selectedPackage, permission))
    }

    fun getPermissions(selectedDevice: String, selectedPackage: String) {
        val result = launchShellCommand(selectedDevice, Commands.getGrantedPermissions(selectedPackage))
        log("output :$result")
    }


    // Private
    private fun launchShell(selectedDevice: String, command: String) {
        if (selectedDevice == AppStore.ALL_DEVICES) {
            launchShellCommandOnAllDevices(command)
        } else {
            launchShellCommand(selectedDevice, command)
        }
    }

    private fun launchShellCommandOnAllDevices(command: String) {
        devicesInfo().forEach {
            launchShellCommand(it.serial, command)
        }
    }

    private fun launchShellCommand(serial: String, command: String): ArrayList<String> {
        val commandToExecute = "adb -s $serial shell $command"
        log(commandToExecute)
        return cmd.execute(commandToExecute, null, Commands.getPlatformToolsPath())
    }
}
