package terminal.commands

object DumpsysCommands {
    fun getGrantedPermissions(packageName: String) =
        "dumpsys package $packageName | grep permission | grep granted=true"

    fun getPackageActivities(packageName: String) =
        "dumpsys package | grep -i \"$packageName\" | grep Activity"
}