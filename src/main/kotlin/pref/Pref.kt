package pref

import java.util.prefs.Preferences

object Pref {
    val prefs: Preferences by lazy { Preferences.userNodeForPackage(Pref::class.java) }

    var s1: Boolean by preference("s1", false)
    // Example preference with delegation
    var setting: Int by preference("setting", -1)

    // Example preference without delegation
    const val SETTING = "x"
    var x: Boolean
        get() = prefs.getBoolean(SETTING, false)
        set(value) = prefs.putBoolean(SETTING, value)


}

