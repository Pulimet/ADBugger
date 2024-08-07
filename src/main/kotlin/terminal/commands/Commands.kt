package terminal.commands

object Commands {
    fun getDeviceList() = "adb devices"

    // am
    fun getShowHome() = "am start -a android.intent.action.MAIN -c android.intent.category.HOME"
    fun getShowSettings() = "am start com.android.settings"

    // cmd
    fun getDarkModeOff() = "cmd uimode night no"
    fun getDarkModeOn() = "cmd uimode night yes"

    // ports
    fun adbReverse(port: Int) = "reverse tcp:$port tcp:$port"
    fun adbReverseList() = "adb reverse --list"

    // Settings
    fun getChangeFontSize(d: Double) = "settings put system font_scale $d"

    // wm
    fun getChangeDisplayDensity(density: String) = "wm density $density"

    // pull
    fun getAdbPull(filename: String) = "pull /sdcard/$filename ~/Desktop/$filename"
}
