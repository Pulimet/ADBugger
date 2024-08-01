package adb

object Commands {
    private const val KEYCODE_BACK = 4
    private const val KEYCODE_DPAD_UP = 19
    private const val KEYCODE_DPAD_DOWN = 20
    private const val KEYCODE_DPAD_LEFT = 21
    private const val KEYCODE_DPAD_RIGHT = 22
    private const val KEYCODE_POWER = 26
    private const val KEYCODE_TAB = 61
    private const val KEYCODE_ENTER = 66
    private const val KEYCODE_DEL = 67

    fun getLaunchCommand(packageName: String) =
        "monkey -p $packageName -c android.intent.category.LAUNCHER 1"

    fun getCloseCommand(packageName: String) = "am force-stop  $packageName"
    fun getClearDataCommand(packageName: String) = "pm clear $packageName"
    fun getUninstallCommand(packageName: String) = "uninstall $packageName"
    fun getApkPathCommand(packageName: String) = "pm path $packageName"
    fun getShowHome() = "am start -a android.intent.action.MAIN -c android.intent.category.HOME"
    fun getShowSettings() = "am start com.android.settings"
    fun getPressBack() = getInputKeyEventFor(KEYCODE_BACK)
    fun getPressTab() = getInputKeyEventFor(KEYCODE_TAB)
    fun getPressEnter() = getInputKeyEventFor(KEYCODE_ENTER)
    fun getPressPower() = getInputKeyEventFor(KEYCODE_POWER)
    fun getUp() = getInputKeyEventFor(KEYCODE_DPAD_UP)
    fun getDown() = getInputKeyEventFor(KEYCODE_DPAD_DOWN)
    fun getLeft() = getInputKeyEventFor(KEYCODE_DPAD_LEFT)
    fun getRight() = getInputKeyEventFor(KEYCODE_DPAD_RIGHT)
    fun getDelete() = getInputKeyEventFor(KEYCODE_DEL)
    fun getDarkModeOff() = "cmd uimode night no"
    fun getDarkModeOn() = "cmd uimode night yes"
    fun getKillEmulatorBySerial() = "emu kill"
    fun getWipeDataEmulatorByName(emulatorName: String) = "emulator -avd $emulatorName -wipe-data"
    fun getLaunchEmulator(emulatorName: String) = "emulator -avd $emulatorName -netdelay none -netspeed full"
    fun sendTextCommand(value: String) = "input text $value"
    fun sendInputCommand(input: Int) = getInputKeyEventFor(input)
    fun adbReverse(port: Int) = "reverse tcp:$port tcp:$port"
    fun adbReverseList() = "adb reverse --list"
    fun getDeviceList() = "adb devices"
    fun getPackageList() = "pm list packages"
    fun getEmulatorList() = "emulator -list-avds"
    fun getRevokeAllPermissions(packageName: String) = "pm reset-permissions -p $packageName"
    fun getChangeFontSize(d: Double) = "settings put system font_scale $d"
    fun getChangeDisplayDensity(density: String) = "wm density $density"
    fun getAdbInstall(apkPath: String) = "install $apkPath"
    fun getAdbPull(filename: String) = "pull /sdcard/$filename ~/Desktop/$filename"

    fun addSpecificPermission(packageName: String, permission: String) =
        "pm grant $packageName android.permission.$permission"

    fun revokeSpecificPermission(packageName: String, permission: String) =
        "pm revoke $packageName android.permission.$permission"

    fun getGrantedPermissions(packageName: String) =
        "dumpsys package $packageName | grep permission | grep granted=true"

    fun getLogCatCommand(selectedTarget: String, buffer: String, format: String, priorityLevel: String): String {
        val s = if (selectedTarget.isEmpty()) "" else " -s $selectedTarget"
        val b = if (buffer.isEmpty() || buffer == "default") "" else " -b $buffer"
        val v = if (format.isEmpty() || format == "threadtime") "" else " -v $format"
        val p = if (priorityLevel.isEmpty() || priorityLevel == "V") "" else " \"*:$priorityLevel\""
        return "adb$s logcat$b$v$p"
    }

    // Path
    fun getPlatformToolsPath() = "~/Library/Android/sdk/platform-tools/"

    fun getEmulatorPath() = "~/Library/Android/sdk/emulator/"

    // Private
    private fun getInputKeyEventFor(keycode: Int) = "input keyevent $keycode"
}
