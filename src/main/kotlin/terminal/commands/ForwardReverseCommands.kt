package terminal.commands

object ForwardReverseCommands {
    fun adbReverse(portFrom: Int, portTo: Int) = "reverse tcp:$portFrom tcp:$portTo"
    fun adbForward(portFrom: Int, portTo: Int) = "forward tcp:$portFrom tcp:$portTo"
    fun adbReverseList() = "reverse --list"
    fun adbForwardList() = "forward --list"
    fun adbReverseListClear() = "reverse --remove-all"
    fun adbForwardListClear() = "forward --remove-all"
}