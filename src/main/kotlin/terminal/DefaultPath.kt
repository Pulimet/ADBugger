package terminal

import utils.PlatformX

object DefaultPath {
    fun getPlatformToolsDefaultPath(): String {
        return if (PlatformX.isWindows) {
            "${System.getProperty("user.home")}\\AppData\\Local\\Android\\sdk\\platform-tools"
        } else {
            "~/Library/Android/sdk/platform-tools/"
        }
    }

    fun getEmulatorDefaultPath(): String {
        return if (PlatformX.isWindows) {
            "${System.getProperty("user.home")}\\AppData\\Local\\Android\\sdk\\emulator"
        } else {
            "~/Library/Android/sdk/emulator/"
        }
    }
}