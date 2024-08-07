package terminal.commands

object PackagesCommands {
    fun getLaunchCommand(packageName: String) =
        "monkey -p $packageName -c android.intent.category.LAUNCHER 1"

    fun getAdbInstall(apkPath: String) = "install $apkPath"
    fun getUninstallCommand(packageName: String) = "uninstall $packageName"
    fun getCloseCommand(packageName: String) = "am force-stop  $packageName"
    fun getClearDataCommand(packageName: String) = "pm clear $packageName"
    fun getApkPathCommand(packageName: String) = "pm path $packageName"
    fun getPackageList() = "pm list packages"
}