package terminal.commands

object CmdCommands {
    // cmd
    fun getDarkModeOff() = "cmd uimode night no"
    fun getDarkModeOn() = "cmd uimode night yes"

    // Get airplane mode:  cmd connectivity airplane-mode
    fun getAirplaneOn() = "cmd connectivity airplane-mode enable"
    fun getAirplaneOff() = "cmd connectivity airplane-mode disable"

    fun getWifiOn() = "cmd -w wifi set-wifi-enabled enabled"
    fun getWifiOff() = "cmd -w wifi set-wifi-enabled disabled"
}