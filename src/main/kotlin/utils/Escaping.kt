package utils

object Escaping {
    fun escapeForAdbShellInputText(text: String): String {
        return text
            .replace("\\", "\\\\\\\\")
            .replace("\"", "\\\\\\\"")
            .replace("`", "\\\\\\`")
            .replace("'", "\\'")
            .replace("&", "\\&")
            .replace("*", "\\*")
            .replace("(", "\\(")
            .replace(")", "\\)")
            .replace("<", "\\<")
            .replace(">", "\\>")
            .replace(";", "\\;")
            .replace("|", "\\|")
            .replace("~", "\\~")
            .replace("¬", "\\¬")
            .replace("{", "\\{")
            .replace("}", "\\}")
            .replace(" ", "%s")
    }
}