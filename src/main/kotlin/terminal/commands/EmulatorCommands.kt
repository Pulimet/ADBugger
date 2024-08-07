package terminal.commands

object EmulatorCommands {
    fun getEmulatorList() = "emulator -list-avds"
    fun getWipeDataEmulatorByName(emulatorName: String) = "emulator -avd $emulatorName -wipe-data"
    fun getLaunchEmulator(emulatorName: String) = "emulator -avd $emulatorName -netdelay none -netspeed full"
    fun getKillEmulatorBySerial() = "emu kill"


    // Makes all TCP connections through a specified HTTP/HTTPS proxy.
    // If your emulator must access the internet through a proxy server,
    // you can use this option or the http_proxy environment variable to set up the appropriate redirection.
    // Proxy can be one of the following:
    // http://server:port or http://username:password@server:port  (The http:// prefix can be omitted.)
    // For example:
    // emulator @Pixel8_API_34 -http-proxy myserver:1981

    // Specifies the physical RAM size, from 1536 to 8192 MBs. For example:
    // emulator @Pixel8_API_34 -memory 2048

    // Specifies the filename and path to an SD card partition image file. For example:
    // emulator @Pixel8_API_34 -sdcard C:/sd/sdcard.img

    // TODO Continue from here: -netdelay delay
    // TODO At EmulatorsPage add 3 tabs: Run, Create, Install
    // https://developer.android.com/studio/run/emulator-commandline

    // "android-emulator:install-package": "${ANDROID_HOME}/tools/bin/sdkmanager --install 'system-images;android-31;default;x86_64'",
    // "android-emulator:create-avd": "rm -f ${HOME}/.android/avd/${emulatorName}.avd/*.lock && ${ANDROID_HOME}/tools/bin/avdmanager --verbose create avd --force --name 'Pixel_4_API_30' --package 'system-images;android-31;default;x86_64' -d 'pixel_xl'",
    // "android-emulator:start": "${ANDROID_HOME}/emulator/emulator -avd Pixel_4_API_30 -cores 2 -gpu auto -accel on -memory 1536 -noaudio  -no-boot-anim -snapshot pac -no-snapshot-save -partition-size 1536",

    // Quick Boot
    //-no-snapshot-load	Performs a cold boot and saves the emulator state on exit.
    //-no-snapshot-save	Performs a quick boot if possible, but does not save the emulator state on exit.
    //-no-snapshot	Disables the Quick Boot feature completely and doesn't load or save the emulator state.
}