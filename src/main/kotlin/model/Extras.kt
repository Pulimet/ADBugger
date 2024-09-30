package model

data class Extras(val key: String, val value: String, val type: ExtraType) {
    fun toList() = listOf(key, value, type.toString())
}

enum class ExtraType {
    STRING, INT, LONG, FLOAT, BOOLEAN, URI;

    companion object {
        fun fromName(name: String): ExtraType = valueOf(name.uppercase())
    }

    override fun toString() =
        when (this) {
            STRING -> "String"
            INT -> "Int"
            LONG -> "Long"
            FLOAT -> "Float"
            BOOLEAN -> "Boolean"
            URI -> "URI"
        }
}
