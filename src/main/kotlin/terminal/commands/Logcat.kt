package terminal.commands

object Logcat {

    fun getLogCatCommand(
        selectedTarget: String,
        buffer: String,
        format: String,
        priorityLevel: String,
        tag: String
    ): String {
        val s = if (selectedTarget.isEmpty()) "" else " -s $selectedTarget"
        val b = if (buffer.isEmpty() || buffer == "default") "" else " -b $buffer"
        val v = if (format.isEmpty() || format == "threadtime") "" else " -v $format"
        val t = tag.ifEmpty { "*" }
        val p = if (priorityLevel.isEmpty()) "" else " \"$t:$priorityLevel\""
        return "adb$s logcat$b$v$p"
    }

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

    val formatLstDetails = listOf(
        "Displays: date, invocation time, priority, tag, PID, and TID",
        "Displays: priority, tag, and PID",
        "Displays all metadata fields and separate messages with blank lines.",
        "Displays PID only.",
        "Displays the raw log message with no other metadata fields.",
        "Displays: priority and tag only.",
        "A legacy format that shows priority, PID, and TID",
        "Displays: date, invocation time, priority, tag, and PID",
    )

    // Tags selection, default: *:V // adb logcat "*:I" // adb logcat "tagName:I"
    // adb logcat ActivityManager:I tagName:D *:S
    // "S" - Silent (highest priority, where nothing is ever printed)
    val priorityLevelList = listOf(
        "V", // Verbose - default
        "D", // Debug
        "I", // Info
        "W", // Warning
        "E", // Error
        "F", // Fatal
    )

    val priorityLevelListDetails = listOf(
        "Verbose",
        "Debug",
        "Info",
        "Warning",
        "Error",
        "Fatal",
    )
}
