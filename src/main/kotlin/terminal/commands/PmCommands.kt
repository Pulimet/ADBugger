package terminal.commands

object PmCommands {
    fun getClearDataCommand(packageName: String) = "pm clear $packageName"
    fun getApkPathCommand(packageName: String) = "pm path $packageName"

    // Permissions
    fun addSpecificPermission(packageName: String, permission: String) =
        "pm grant $packageName android.permission.$permission"

    fun revokeSpecificPermission(packageName: String, permission: String) =
        "pm revoke $packageName android.permission.$permission"

    fun getRevokeAllPermissions(packageName: String) = "pm reset-permissions -p $packageName"

    // Device package list
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