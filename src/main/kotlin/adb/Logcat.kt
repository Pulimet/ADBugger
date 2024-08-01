package adb

object Logcat {
    // Multiple -b parameters or comma separated list of buffers are allowed. Buffers are interleaved.
    // Default -b main,system,crash,kernel.
    val bufferList = listOf(
        "default", //  Reports main, system, and crash buffers.
        "all", // Views all buffers.
        "crash", // Views the crash log buffer
        "kernel", // Views the kernel log buffer
        "main", // Views the main log buffer, which doesn't contain system and crash log messages.
        "radio", // Views the buffer that contains radio/telephony related messages.
        "system", //  Views the system log buffer
        "events" // Views the interpreted binary system event buffer messages.
    )
    val bufferListDetails = listOf(
        "Reports main, system, and crash buffers",
        "Views all buffers",
        "Views the crash log buffer",
        "Views the kernel log buffer",
        "Views the main log buffer, which doesn't contain system and crash log messages",
        "Views the buffer that contains radio/telephony related messages",
        "Views the system log buffer",
        "Views the interpreted binary system event buffer messages",
    )

    // adb logcat -v brief (Only one could be selected)
    val formatLst = listOf(
        "threadtime", // (default): Displays the date, invocation time, priority, tag, PID, and TID of the thread issuing the message.
        "brief", // Displays priority, tag, and PID of the process issuing the message.
        "long", // Displays all metadata fields and separate messages with blank lines.
        "process", // Displays PID only.
        "raw", // Displays the raw log message with no other metadata fields.
        "tag", // Displays the priority and tag only.
        "thread", // A legacy format that shows priority, PID, and TID of the thread issuing the message.
        "time" // Displays the date, invocation time, priority, tag, and PID of the process issuing the message
    )
}
