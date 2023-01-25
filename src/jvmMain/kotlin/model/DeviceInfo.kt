package model

import com.malinskiy.adam.request.device.DeviceState

data class DeviceInfo(val serial: String, val state: DeviceState, val name: String)