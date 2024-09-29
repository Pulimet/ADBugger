package terminal.commands

object ApkCommands {
    fun getAdbInstall(apkPath: String) = "install $apkPath"
    fun getUninstallCommand(packageName: String) = "uninstall $packageName"
}