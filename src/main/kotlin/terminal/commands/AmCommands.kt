package terminal.commands

import model.Extras

object AmCommands {
    const val DEFAULT_ACTION_CATEGORY =
        "-a android.intent.action.VIEW -c android.intent.category.DEFAULT"

    fun getShowHome() = "am start -a android.intent.action.MAIN -c android.intent.category.HOME"
    fun getShowSettings() = "am start com.android.settings"
    fun getCloseCommand(packageName: String) = "am force-stop  $packageName"

    fun getOpenPackageCommand(
        selectedActivity: String,
        extrasMap: List<Extras>
    ): String {
        val extras = extrasMap.map {
            parseExtra(it)

        }
        return "am start -n $selectedActivity $DEFAULT_ACTION_CATEGORY ${extras.joinToString(" ")}"
    }

    private fun parseExtra(it: Extras) = when (it.type.toString()) {
        "String" -> "--es ${it.key} ${it.value}"
        "Int" -> "--ei ${it.key} ${it.value}"
        "Long" -> "--el ${it.key} ${it.value}"
        "Float" -> "--ef ${it.key} ${it.value}"
        "Boolean" -> "--ez ${it.key} ${it.value}"
        "URI" -> "--eu ${it.key} ${it.value}"
        else -> error("Unsupported type ${it.key}")
    }

    val typesLists = listOf(
        "String",
        "Int",
        "Long",
        "Float",
        "Boolean",
        "URI",
    )
    val typesListsDetails = listOf(
        "--es - Add String as value",
        "--ei - Add Int as value",
        "--el - Add Long as value",
        "--ef - Add Float as value",
        "--ez - Add Boolean as value",
        "--eu - Add URI data as value",
    )
}

/*
adb shell am start  -e foo bar -e foo2 bar2 -n your.package.name
adb shell am start -n com.example.app/.MainActivity -e "name" $userName -e "f" $f

--ecn extra_key extra_component_name_value
Add a component name, which is converted and passed as a ComponentName object.
--eia extra_key extra_int_value[,extra_int_value...]
Add an array of integers.
--ela extra_key extra_long_value[,extra_long_value...]
Add an array of longs.
--efa extra_key extra_float_value[,extra_float_value...]
 */