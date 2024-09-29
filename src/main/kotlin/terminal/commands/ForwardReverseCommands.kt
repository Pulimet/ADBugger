package terminal.commands

object ForwardReverseCommands {
    fun adbReverse(port: Int) = "reverse tcp:$port tcp:$port"
    fun adbReverseList() = "adb reverse --list"
}