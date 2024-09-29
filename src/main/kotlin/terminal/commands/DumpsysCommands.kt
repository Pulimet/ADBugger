package terminal.commands

object DumpsysCommands {
    fun getGrantedPermissions(packageName: String) =
        "dumpsys package $packageName | grep permission | grep granted=true"
}