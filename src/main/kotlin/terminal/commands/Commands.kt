package terminal.commands

object Commands {
    fun getDeviceList() = "adb devices"

    // am
    fun getShowHome() = "am start -a android.intent.action.MAIN -c android.intent.category.HOME"
    fun getShowSettings() = "am start com.android.settings"

    // cmd
    fun getDarkModeOff() = "cmd uimode night no"
    fun getDarkModeOn() = "cmd uimode night yes"

    // =======>>> Airplane mode
    // Get airplane mode:
    // > adb shell cmd connectivity airplane-mode
    // disabled
    // Turn airplane mode enable/disable:
    // > adb shell cmd connectivity airplane-mode enable
    // > adb shell cmd connectivity airplane-mode disable
    // ---- Hm... should check which works on real device/emulator
    // To enable the Airplane mode use the following commands:
    // > adb shell settings put global airplane_mode_on 1
    // > adb shell am broadcast -a android.intent.action.AIRPLANE_MODE
    // ---- Hm....
    // > adb shell am broadcast -a android.intent.action.AIRPLANE_MODE --ez state true/false
    // -----
    // To enable/disable the Airplane mode,use the following adb commands:
    // > adb shell am start -a android.settings.AIRPLANE_MODE_SETTINGS
    // #toggle/opposite
    // > adb shell input keyevent 19 & adb shell input keyevent 19 & adb shell input keyevent 23

    // WIFI
    // enable Wi-Fi (turn on):
    // > adb shell cmd -w wifi set-wifi-enabled enabled
    // disable Wi-Fi (turn off):
    // > adb shell cmd -w wifi set-wifi-enabled disabled
    // ------
    // > adb shell am start -a android.intent.action.MAIN -n com.android.settings/.wifi.WifiSettings
    // > adb shell input keyevent 20 & adb shell input keyevent 23
    // ------
    // > adb shell svc data disable    # Disable data
    // > adb shell svc wifi enable    # Enable Wi-Fi

    // Rotation
    // > adb shell settings put system accelerometer_rotation 0  #disable auto-rotate
    // > adb shell settings put system user_rotation 3  #270° clockwise
    // -----
    // Disable auto rotation
    // > adb shell content insert --uri content://settings/system --bind name:s:accelerometer_rotation --bind value:i:0
    // Force landscape
    // > adb shell content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:1
    // Rotate back to portrait
    // > adb shell content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:0
    // Upside down
    // > adb shell content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:2
    // Rotate to landscape, right
    // > adb shell content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:3

    // Mock location
    // > adb shell appops set io.appium.settings android:mock_location allow
    // Start sending scheduled updates for mock location:
    //For API versions 26 and above:
    //$ adb shell am start-foreground-service --user 0 -n io.appium.settings/.LocationService --es longitude {longitude-value} --es latitude {latitude-value}
    //For older versions:
    //$ adb shell am startservice --user 0 -n io.appium.settings/.LocationService --es longitude {longitude-value} --es latitude {latitude-value}
    //To stop sending mock locations and clean up, execute:
    //$ adb shell am stopservice io.appium.settings/.LocationService

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
