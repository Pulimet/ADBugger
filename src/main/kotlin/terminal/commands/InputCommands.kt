package terminal.commands

object InputCommands {
    private const val KEYCODE_BACK = 4
    private const val KEYCODE_DPAD_UP = 19
    private const val KEYCODE_DPAD_DOWN = 20
    private const val KEYCODE_DPAD_LEFT = 21
    private const val KEYCODE_DPAD_RIGHT = 22
    private const val KEYCODE_POWER = 26
    private const val KEYCODE_TAB = 61
    private const val KEYCODE_ENTER = 66
    private const val KEYCODE_DEL = 67
    private const val KEYCODE_APP_SWITCH = 187


    fun getPressTab() = getInputKeyEventFor(KEYCODE_TAB)
    fun getPressEnter() = getInputKeyEventFor(KEYCODE_ENTER)
    fun getPressPower() = getInputKeyEventFor(KEYCODE_POWER)
    fun getUp() = getInputKeyEventFor(KEYCODE_DPAD_UP)
    fun getDown() = getInputKeyEventFor(KEYCODE_DPAD_DOWN)
    fun getLeft() = getInputKeyEventFor(KEYCODE_DPAD_LEFT)
    fun getRight() = getInputKeyEventFor(KEYCODE_DPAD_RIGHT)
    fun getDelete() = getInputKeyEventFor(KEYCODE_DEL)
    fun getPressBack() = getInputKeyEventFor(KEYCODE_BACK)

    fun getShowRecentApps() = getInputKeyEventFor(KEYCODE_APP_SWITCH)

    fun sendTextCommand(value: String) = "input text $value"

    fun sendInputCommand(input: Int) = getInputKeyEventFor(input)
    // Private
    private fun getInputKeyEventFor(keycode: Int) = "input keyevent $keycode"
}