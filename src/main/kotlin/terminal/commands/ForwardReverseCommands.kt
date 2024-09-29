package terminal.commands

object ForwardReverseCommands {
    fun adbReverse(portFrom: Int, portTo: Int) = "reverse tcp:$portFrom tcp:$portTo"
    fun adbForward(portFrom: Int, portTo: Int) = "forward tcp:$portFrom tcp:$portTo"
    fun adbReverseList() = "adb reverse --list"
}