package terminal.commands

object Commands {
    fun getDeviceList() = "adb devices"

    // am
    fun getShowHome() = "am start -a android.intent.action.MAIN -c android.intent.category.HOME"
    fun getShowSettings() = "am start com.android.settings"

    // cmd
    fun getDarkModeOff() = "cmd uimode night no"
    fun getDarkModeOn() = "cmd uimode night yes"

    // Get airplane mode:  cmd connectivity airplane-mode
    fun getAirplaneOn() = "cmd connectivity airplane-mode enable"
    fun getAirplaneOff() = "cmd connectivity airplane-mode disable"

    fun getWifiOn() = "cmd -w wifi set-wifi-enabled enabled"
    fun getWifiOff() = "cmd -w wifi set-wifi-enabled disabled"

    fun getGpdOn() = "settings put secure location_mode 3"
    fun getGpsOff() = "settings put secure location_mode 0"

    fun getRotationAutoTurnOff() ="content insert --uri content://settings/system --bind name:s:accelerometer_rotation --bind value:i:0"
    fun getRotationAutoTurnOn() ="content insert --uri content://settings/system --bind name:s:accelerometer_rotation --bind value:i:1"
    fun getRotationLandscape() = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:1"
    fun getRotationPortrait() = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:0"
    fun getRotationUpSideDown() = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:2"
    fun getRotationLandscape2() = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:3"

    // ports
    fun adbReverse(port: Int) = "reverse tcp:$port tcp:$port"
    fun adbReverseList() = "adb reverse --list"

    // Settings
    fun getChangeFontSize(d: Double) = "settings put system font_scale $d"

    // wm
    fun getChangeDisplayDensity(density: String) = "wm density $density"

    // pull
    fun getAdbPull(filename: String) = "pull /sdcard/$filename ~/Desktop/$filename"

    // TODO
    // adb shell dumpsys meminfo

}
