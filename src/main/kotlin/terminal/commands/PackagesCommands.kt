package terminal.commands

object PackagesCommands {
    fun getLaunchCommand(packageName: String) =
        "monkey -p $packageName -c android.intent.category.LAUNCHER 1"

    fun getAdbInstall(apkPath: String) = "install $apkPath"
    fun getUninstallCommand(packageName: String) = "uninstall $packageName"
    fun getCloseCommand(packageName: String) = "am force-stop  $packageName"
    fun getClearDataCommand(packageName: String) = "pm clear $packageName"
    fun getApkPathCommand(packageName: String) = "pm path $packageName"

    val optionsList = listOf(
        "-3",
        "",
        "-s",
        "-e",
        "-d",
        "-f",
        "--show-versioncode",
        "-a",
        "--apex-only",
        "-i",
        "-U",
        "-u",
    )
    val optionsListDetails = listOf(
        "Show only third party packages",
        "Show all packages",
        "Show only system packages",
        "Show only enabled packages",
        "Show only disabled packages",
        "Show associated APK file",
        "Show package version code",
        "All known packages excluding APEXes (Android Pony Express)",
        "Show only APEX packages",
        "Show installer from the packages",
        "Show packages UID",
        "Show uninstalled packages"
    )

    fun getPackageList(selectedOption: String) = "pm list packages $selectedOption"
}