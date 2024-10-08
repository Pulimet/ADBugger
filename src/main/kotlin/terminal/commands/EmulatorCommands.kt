package terminal.commands

import model.RunEmulatorParams

object EmulatorCommands {
    fun getEmulatorList() = "emulator -list-avds"
    fun getWipeDataEmulatorByName(emulatorName: String) = "emulator -avd $emulatorName -wipe-data"
    fun getKillEmulatorBySerial() = "emu kill"

    fun getLaunchEmulator(emulatorName: String, params: RunEmulatorParams): String {
        val pr = if (params.proxy.isEmpty()) "" else " -http-proxy ${params.proxy}"
        val r = if (params.ram in 1536..8192) " -memory ${params.ram}" else ""
        val p = if (params.partition in 800..8192) "  -partition ${params.partition}" else ""
        val q = if (params.quickBoot == "enabled") "" else " ${params.quickBoot}"
        val t = if (params.touchMode == "touch") "" else " -screen ${params.touchMode}"
        val b = if (params.bootAnimEnabled) "" else " -no-boot-anim"
        val a = if (params.audioEnabled) "" else " -noaudio"

        // Disables network throttling. For example: emulator @Pixel8_API_34 -netfast
        // This option is the same as specifying -netspeed full -netdelay none. These are the default values for these options.
        val netfast = if (params.latency == "none" && params.speed == "full") " -netfast" else ""
        val l = if (params.latency == "none") "" else " -netdelay ${params.latency}"
        val s = if (params.speed == "full") "" else " -netspeed ${params.speed}"

        return "emulator -avd $emulatorName$netfast$l$s$pr$r$p$q$b$a$t"
    }

    // Proxy
    fun getSetProxy(proxy: String) = "settings put global http_proxy $proxy"
    fun getRemoveProxy() = "settings put global http_proxy :0"
    fun getFetchProxy() = "settings get global http_proxy"

    // Sets network latency emulation to one of the following delay values in milliseconds:
    // num - Specifies exact latency.
    // min:max - Specifies individual minimum and maximum latencies.
    val networkDelayList = listOf(
        "none",
        "gsm",
        "hscsd",
        "gprs",
        "edge",
        "umts",
        "hsdpa",
        "lte",
        "evdo",
    )

    val networkDelayListDetails = listOf(
        "No latency, the default (min 0, max 0)",
        "GSM/CSD (min 150, max 550)",
        "HSCSD (min 80, max 400)",
        "GPRS (min 35, max 200)",
        "EDGE/EGPRS (min 80, max 400)",
        "UMTS/3G (min 35, max 200)",
        "HSDPA (min 0, max 0)",
        "LTE (min 0, max 0)",
        "EVDO (min 0, max 0)",
    )


    // Sets the network speed emulation. Specifies the maximum network upload
    // and download speeds with one of the following speed values in kbps:
    // num - Specifies both upload and download speed.
    // up:down - Specifies individual up and down speeds.
    val networkSpeedList = listOf(
        "full",
        "gsm",
        "hscsd",
        "gprs",
        "edge",
        "umts",
        "hsdpa",
        "lte",
        "evdo",
    )

    val networkSpeedListDetails = listOf(
        "No limit, the default (up: 0.0, down: 0.0)",
        "GSM/CSD (up: 14.4, down: 14.4)",
        "HSCSD (up: 14.4, down: 57.6)",
        "GPRS (up: 28.8, down: 57.6)",
        "EDGE/EGPRS (up: 473.6, down: 473.6)",
        "UMTS/3G (up: 384.0, down: 384.0)",
        "HSDPA (up: 5760.0, down: 13,980.0)",
        "LTE (up: 58,000, down: 173,000)",
        "EVDO (up: 75,000, down: 280,000)",
    )

    val quickBootList = listOf(
        "enabled",
        "-no-snapshot-load",
        "-no-snapshot-save",
        "-no-snapshot",
    )

    val quickBootListDetails = listOf(
        "By default Quick Boot feature enabled",
        "Performs a cold boot and saves the emulator state on exit",
        "Performs a quick boot if possible, but does not save the emulator state on exit",
        "Disables the Quick Boot feature completely and doesn't load or save the emulator state",
    )

    val touchList = listOf(
        "touch",
        "multi-touch",
        "no-touch",
    )

    val touchListDetails = listOf(
        "Emulates a touch screen (default)",
        "Emulates a multi-touch screen",
        "Disables touch and multi-touch screen emulation",
    )


    // TODO Install tab
    // "install-package": "${ANDROID_HOME}/tools/bin/sdkmanager --install 'system-images;android-31;default;x86_64'",

    // TODO Create tab
    // "create-avd": "rm -f ${HOME}/.android/avd/${emulatorName}.avd/*.lock && ${ANDROID_HOME}/tools/bin/avdmanager --verbose create avd --force --name 'Pixel_4_API_30' --package 'system-images;android-31;default;x86_64' -d 'pixel_xl'",

    // List used above:
    // -cores 2  - Specifies the number of CPU cores to use. The default is 2.
    // -gpu auto  - Specifies the GPU emulation mode. The default is auto.
    // -accel on  - Enables emulator acceleration. The default is on.
    // -snapshot pac - Loads a snapshot at startup. The snapshot must be saved with the -no-snapshot-save option.


    // ------------
    // Not sure if we need below properties:

    // Captures network packets and stores them in a file. For example:
    // emulator @Pixel8_API_34 -tcpdump /path/dumpfile.cap
    // Use this option to begin capturing all network packets that are sent through the virtual Ethernet LAN of the emulator.
    // Afterward, you can use a tool like Wireshark to analyze the traffic.
    // Note that this option captures all ethernet packets and isn't limited to TCP connections.

    // Timezones - Sets the time zone for the virtual device to timezone instead of the host time zone. For example:
    // emulator @Pixel8_API_34 -timezone Europe/Paris
    // By default, the emulator uses the time zone of your development computer.
    // Use this option to specify a different time zone or if the automatic detection isn't working correctly.
    // The timezone value must be in zoneinfo format, which is area/location or area/subarea/location. For example:
    // America/Los_Angeles, Europe/Paris, America/Argentina/Buenos_Aires
    // The specified time zone must be in the: https://www.iana.org/time-zones

    //To get a list of emulator environment variables, enter the following command:
    // emulator -help-environment

    // Disables graphical window display on the emulator. This option is useful when running the emulator
    // on servers that have no display. You can access the emulator through adb or the console. For example:
    // emulator @Pixel8_API_34 -no-window

    // Version - Displays the emulator version number. For example:
    // emulator @Pixel8_API_34 -version

    // SC card - Specifies the filename and path to an SD card partition image file. For example:
    // emulator @Pixel8_API_34 -sdcard C:/sd/sdcard.img

    // Sets the TCP port number that's used for the console and adb. For example:
    // emulator @Pixel8_API_34 -port 5556
    // Sets the TCP port numbers that are used for the console, adb, and other services. For example:
    // emulator @Pixel8_API_34 -ports 5556,5559

    // -accel mode  - Specifies the emulator acceleration mode. The mode can be one of the following: auto, off, or on.
    // -accel-check - Check whether the emulator can be accelerated.
    // -no-accel - Disables emulator acceleration.
    // -engine engine - Specifies the emulator engine to use. The engine can be one of the following: auto, classic, or qemu2.
    // -gpu mode - Specifies the emulator GPU mode. The mode can be one of the following: auto, off, host, or swiftshader_indirect.
    // -nojni - Disables JNI checks in the Dalvik/ART runtime.
    // -selinux {disabled|permissive} - Sets the SELinux mode for the emulator. The mode can be one of the following: disabled or permissive.


}
