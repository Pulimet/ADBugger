package terminal.commands

object ContentCommands {
    // content insert
    fun getRotationAutoTurnOff() ="content insert --uri content://settings/system --bind name:s:accelerometer_rotation --bind value:i:0"
    fun getRotationAutoTurnOn() ="content insert --uri content://settings/system --bind name:s:accelerometer_rotation --bind value:i:1"
    fun getRotationLandscape() = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:1"
    fun getRotationPortrait() = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:0"
    fun getRotationUpSideDown() = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:2"
    fun getRotationLandscape2() = "content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:3"
}