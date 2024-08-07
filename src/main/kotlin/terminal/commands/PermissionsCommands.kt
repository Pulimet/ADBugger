package terminal.commands

object PermissionsCommands {
    fun addSpecificPermission(packageName: String, permission: String) =
        "pm grant $packageName android.permission.$permission"

    fun revokeSpecificPermission(packageName: String, permission: String) =
        "pm revoke $packageName android.permission.$permission"

    fun getGrantedPermissions(packageName: String) =
        "dumpsys package $packageName | grep permission | grep granted=true"

    fun getRevokeAllPermissions(packageName: String) = "pm reset-permissions -p $packageName"
}