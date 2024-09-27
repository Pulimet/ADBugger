package terminal.commands

object Commands {
    fun getDeviceList() = "adb devices"

    // ports
    fun adbReverse(port: Int) = "reverse tcp:$port tcp:$port"
    fun adbReverseList() = "adb reverse --list"

    // pull
    fun getAdbPull(filename: String) = "pull /sdcard/$filename ~/Desktop/$filename"

    fun getDeviceProps() = "getprop"
    // adb shell getprop ro.build.version.release

}
