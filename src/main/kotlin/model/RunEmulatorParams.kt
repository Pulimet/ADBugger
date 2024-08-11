package model

data class RunEmulatorParams(
    val proxy: String = "",
    val ram: Int = 0,
    val partition: Int = 0,
    val latency: String = "none",
    val speed: String = "full",
    val quickBoot: String = "enabled",
    val touchMode: String = "touch",
    val bootAnimEnabled: Boolean = true,
    val audioEnabled: Boolean = true,
)