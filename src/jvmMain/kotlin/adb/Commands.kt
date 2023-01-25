package adb

object Commands {
    private const val KEYCODE_BACK = 4
    private const val KEYCODE_TAB = 61
    private const val KEYCODE_ENTER = 66

    fun getLaunchCommandByPackageName(packageName: String) =
        "monkey -p $packageName -c android.intent.category.LAUNCHER 1"

    fun getCloseCommandByPackageName(packageName: String) = "am force-stop  $packageName"
    fun getClearDataCommandByPackageName(packageName: String) = "pm clear $packageName"
    fun getShowHome() = "am start -a android.intent.action.MAIN -c android.intent.category.HOME"
    fun getPressBack() = getInputKeyEventFor(KEYCODE_BACK)
    fun getPressTab() = getInputKeyEventFor(KEYCODE_TAB)
    fun getPressEnter() =  getInputKeyEventFor(KEYCODE_ENTER)

    //const val killEmulatorsCommand = "adb devices | grep emulator | cut -f1 | while read line; do adb -s \$line emu kill; done"
    fun getKillEmulator(serial: String) = "adb -s $serial emu kill"
    // Private
    private fun getInputKeyEventFor(keycode: Int) = "input keyevent $keycode"
}