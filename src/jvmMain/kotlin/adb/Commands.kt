package adb

object Commands {
    @Suppress("SameParameterValue")
    fun getLaunchCommandByPackageName(packageName: String) =
        "monkey -p $packageName -c android.intent.category.LAUNCHER 1"

    @Suppress("SameParameterValue")
    fun getCloseCommandByPackageName(packageName: String) =
        "am force-stop  $packageName"

    //const val killEmulatorsCommand = "adb devices | grep emulator | cut -f1 | while read line; do adb -s \$line emu kill; done"
    fun getKillEmulator(serial: String) = "adb -s $serial emu kill"
}