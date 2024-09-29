package terminal.commands

object Commands {
    fun getDeviceList() = "adb devices"

    // Pull
    fun getAdbPull(filename: String) = "pull /sdcard/$filename ~/Desktop/$filename"

    // Props
    fun getDeviceProps() = "getprop"

    // Monkey
    fun getLaunchCommand(packageName: String) =
        "monkey -p $packageName -c android.intent.category.LAUNCHER 1"

}
