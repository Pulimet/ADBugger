package terminal.commands

object SettingsCommands {
    fun getGpdOn() = "settings put secure location_mode 3"
    fun getGpsOff() = "settings put secure location_mode 0"
    fun getChangeFontSize(d: Double) = "settings put system font_scale $d"
}