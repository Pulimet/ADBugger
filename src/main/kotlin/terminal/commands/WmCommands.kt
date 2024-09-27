package terminal.commands

object WmCommands {
    // wm density reset/300
    fun getChangeDisplayDensity(density: String) = "wm density $density"
    // wm size reset/2048x1536
    fun getChangeDisplaySize(size: String) = "wm size $size"
}