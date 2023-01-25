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

    fun getLaunchCommand(packageName: String) =
        "monkey -p $packageName -c android.intent.category.LAUNCHER 1"

    fun getCloseCommand(packageName: String) = "am force-stop  $packageName"
    fun getClearDataCommand(packageName: String) = "pm clear $packageName"
    fun getUninstallCommand(packageName: String) = "uninstall $packageName"
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
    fun getDarkModeOff() = "cmd uimode night no"
    fun getDarkModeOn() = "cmd uimode night yes"
    fun getKillEmulator(serial: String) = "adb -s $serial emu kill"


    // Private
    private fun getInputKeyEventFor(keycode: Int) = "input keyevent $keycode"
}