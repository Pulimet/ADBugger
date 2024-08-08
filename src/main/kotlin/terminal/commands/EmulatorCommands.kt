package terminal.commands

object EmulatorCommands {
    fun getEmulatorList() = "emulator -list-avds"
    fun getWipeDataEmulatorByName(emulatorName: String) = "emulator -avd $emulatorName -wipe-data"
    fun getLaunchEmulator(emulatorName: String) = "emulator -avd $emulatorName -netfast"
    fun getKillEmulatorBySerial() = "emu kill"


    // Proxy - Makes all TCP connections through a specified HTTP/HTTPS proxy.
    // http://server:port or http://username:password@server:port  (The http:// prefix can be omitted.)
    // emulator @Pixel8_API_34 -http-proxy myserver:1981

    // RAM - Specifies the physical RAM size, from 1536 to 8192 MBs. For example:
    // emulator @Pixel8_API_34 -memory 2048

    // SC card - Specifies the filename and path to an SD card partition image file. For example:
    // emulator @Pixel8_API_34 -sdcard C:/sd/sdcard.img

    // Network delay - Sets network latency emulation to one of the following delay values in milliseconds:
    // gsm - GSM/CSD (min 150, max 550).
    // hscsd - HSCSD (min 80, max 400).
    // gprs - GPRS (min 35, max 200).
    // edge - EDGE/EGPRS (min 80, max 400).
    // umts - UMTS/3G (min 35, max 200).
    // hsdpa - HSDPA (min 0, max 0).
    // lte - LTE (min 0, max 0).
    // evdo - EVDO (min 0, max 0).
    // none - No latency, the default (min 0, max 0).
    // num - Specifies exact latency.
    // min:max - Specifies individual minimum and maximum latencies.
    // For example:
    // emulator @Pixel8_API_34 -netdelay gsm

    // Disables network throttling. For example: emulator @Pixel8_API_34 -netfast
    // This option is the same as specifying -netspeed full -netdelay none. These are the default values for these options.

    // Network speed - Sets the network speed emulation. Specifies the maximum network upload
    // and download speeds with one of the following speed values in kbps:
    // gsm - GSM/CSD (up: 14.4, down: 14.4).
    // hscsd - HSCSD (up: 14.4, down: 57.6).
    // gprs - GPRS (up: 28.8, down: 57.6).
    // edge - EDGE/EGPRS (up: 473.6, down: 473.6).
    // umts - UMTS/3G (up: 384.0, down: 384.0).
    // hsdpa - HSDPA (up: 5760.0, down: 13,980.0).
    // lte - LTE (up: 58,000, down: 173,000).
    // evdo - EVDO (up: 75,000, down: 280,000).
    // full - No limit, the default (up: 0.0, down: 0.0).
    // num - Specifies both upload and download speed.
    // up:down - Specifies individual up and down speeds.
    //For example:
    //emulator @Pixel8_API_34 -netspeed edge



    // "android-emulator:install-package": "${ANDROID_HOME}/tools/bin/sdkmanager --install 'system-images;android-31;default;x86_64'",
    // "android-emulator:create-avd": "rm -f ${HOME}/.android/avd/${emulatorName}.avd/*.lock && ${ANDROID_HOME}/tools/bin/avdmanager --verbose create avd --force --name 'Pixel_4_API_30' --package 'system-images;android-31;default;x86_64' -d 'pixel_xl'",
    // "android-emulator:start": "${ANDROID_HOME}/emulator/emulator -avd Pixel_4_API_30 -cores 2 -gpu auto -accel on -memory 1536 -noaudio  -no-boot-anim -snapshot pac -no-snapshot-save -partition-size 1536",

    // Quick Boot
    //-no-snapshot-load	Performs a cold boot and saves the emulator state on exit.
    //-no-snapshot-save	Performs a quick boot if possible, but does not save the emulator state on exit.
    //-no-snapshot	Disables the Quick Boot feature completely and doesn't load or save the emulator state.
}
