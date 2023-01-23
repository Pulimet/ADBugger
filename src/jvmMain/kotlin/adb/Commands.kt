package adb

object Commands {
    @Suppress("SameParameterValue")
    fun getLaunchCommandByPackageName(packageName: String) =
        "monkey -p $packageName -c android.intent.category.LAUNCHER 1"

    @Suppress("SameParameterValue")
    fun getCloseCommandByPackageName(packageName: String) =
        "am force-stop  $packageName"
}