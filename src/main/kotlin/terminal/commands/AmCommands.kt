package terminal.commands

import java.net.URI

object AmCommands {
    const val DEFAULT_ACTION_CATEGORY =
        "-a android.intent.action.VIEW -c android.intent.category.DEFAULT"

    fun getShowHome() = "am start -a android.intent.action.MAIN -c android.intent.category.HOME"
    fun getShowSettings() = "am start com.android.settings"
    fun getCloseCommand(packageName: String) = "am force-stop  $packageName"

    inline fun <reified T : Any> getOpenPackageCommand(
        selectedActivity: String,
        extrasMap: Map<String, T>
    ): String {
        val extras = extrasMap.map {
            when (T::class) {
                String::class -> "--es ${keyVal<T>(it)}"
                Int::class -> "--ei ${keyVal<T>(it)}"
                Long::class -> "--el ${keyVal<T>(it)}"
                Float::class -> "--ef ${keyVal<T>(it)}"
                Boolean::class -> "--ez ${keyVal<T>(it)}"
                URI::class -> "--eu ${keyVal<T>(it)}"
                else -> error("Unsupported type ${T::class}")
            }

        }
        return "am start -n $selectedActivity $DEFAULT_ACTION_CATEGORY ${extras.joinToString(" ")}"
    }

    inline fun <reified T> keyVal(it: Map.Entry<String, T>) = "\"${it.key}\" \"${it.value}\""
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