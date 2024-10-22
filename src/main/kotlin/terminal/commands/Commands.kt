package terminal.commands

object Commands {
    fun getDeviceList() = "adb devices"

    // Pull
    fun getAdbPull(filename: String) = "pull /sdcard/$filename ~/Desktop/$filename"

    // Props
    fun getDeviceProps() = "getprop"

    // Monkey
    fun getLaunchCommand(packageName: String) =
        "monkey -p $packageName -c android.intent.category.LAUNCHER 1"

    fun getAdbStartServer() = "adb start-server"

    fun getAdbKillServer() = "adb kill-server"

    fun getAdbUsb() = "adb usb"

    /*

    adb connect <ip:port>
    adb disconnect <ip:port>
    adb disconnect all



port="5555"

# Get device interface
interface=$(adb shell ip addr | awk '/state UP/ {print $2}' | sed 's/.$//'; )
# Get Ip address from interface
ip=$(adb shell ifconfig ${interface} \
    |egrep  -o '(\<([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\>\.){3}\<([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\>' 2> /dev/null \
    |head -n 1)

#
adb tcpip ${port};sleep 0.5
adb connect $ip:${port}; sleep 1.0
adb devices; adb shell


adb reboot

adb reboot recovery

adb reboot-bootloader

adb root //restarts adb with root permissions

adb connect ip_address_of_device
     */
}
