package utils

import androidx.compose.ui.input.key.*

object KeysConverter {

    fun covertEventKeyToKeyCode(event: KeyEvent) = when (event.key) {
        Key.Backspace -> 67
        Key.Enter -> 66
        Key.DirectionUp -> 19
        Key.DirectionDown -> 20
        Key.DirectionLeft -> 21
        Key.DirectionRight -> 22
        Key.X -> if (event.isCtrlPressed || event.isMetaPressed) 277 else -1
        Key.C -> if (event.isCtrlPressed || event.isMetaPressed) 278 else -1
        Key.V -> if (event.isCtrlPressed || event.isMetaPressed) 279 else -1
        else -> -1
    }

    fun convertEventKeyToChar(event: KeyEvent) = when (event.key) {
        Key.A -> if (event.isShiftPressed) "A" else "a"
        Key.B -> if (event.isShiftPressed) "B" else "b"
        Key.C -> if (event.isShiftPressed) "C" else "c"
        Key.D -> if (event.isShiftPressed) "D" else "d"
        Key.E -> if (event.isShiftPressed) "E" else "e"
        Key.F -> if (event.isShiftPressed) "F" else "f"
        Key.G -> if (event.isShiftPressed) "G" else "g"
        Key.H -> if (event.isShiftPressed) "H" else "h"
        Key.I -> if (event.isShiftPressed) "I" else "i"
        Key.J -> if (event.isShiftPressed) "J" else "j"
        Key.K -> if (event.isShiftPressed) "K" else "k"
        Key.L -> if (event.isShiftPressed) "L" else "l"
        Key.M -> if (event.isShiftPressed) "M" else "m"
        Key.N -> if (event.isShiftPressed) "N" else "n"
        Key.O -> if (event.isShiftPressed) "O" else "o"
        Key.P -> if (event.isShiftPressed) "P" else "p"
        Key.Q -> if (event.isShiftPressed) "Q" else "q"
        Key.R -> if (event.isShiftPressed) "R" else "r"
        Key.S -> if (event.isShiftPressed) "S" else "s"
        Key.T -> if (event.isShiftPressed) "T" else "t"
        Key.U -> if (event.isShiftPressed) "U" else "u"
        Key.V -> if (event.isShiftPressed) "V" else "v"
        Key.W -> if (event.isShiftPressed) "W" else "w"
        Key.X -> if (event.isShiftPressed) "X" else "x"
        Key.Y -> if (event.isShiftPressed) "Y" else "y"
        Key.Z -> if (event.isShiftPressed) "Z" else "z"
        Key.Zero -> if (event.isShiftPressed) ")" else "0"
        Key.One -> if (event.isShiftPressed) "!" else "1"
        Key.Two -> if (event.isShiftPressed) "@" else "2"
        Key.Three -> if (event.isShiftPressed) "\\#" else "3"
        Key.Four -> if (event.isShiftPressed) "$" else "4"
        Key.Five -> if (event.isShiftPressed) "%" else "5"
        Key.Six -> if (event.isShiftPressed) "^" else "6"
        Key.Seven -> if (event.isShiftPressed) "\\&" else "7"
        Key.Eight -> if (event.isShiftPressed) "\\*" else "8"
        Key.Nine -> if (event.isShiftPressed) "\\(" else "9"
        else -> {
            ""
        }
    }

    fun convertLetterToKeyCode(letter: String) = when (letter) {
        "A" -> 29
        "B" -> 30
        "C" -> 31
        "D" -> 32
        "E" -> 33
        "F" -> 34
        "G" -> 35
        "H" -> 36
        "I" -> 37
        "J" -> 38
        "K" -> 39
        "L" -> 40
        "M" -> 41
        "N" -> 42
        "O" -> 43
        "P" -> 44
        "Q" -> 45
        "R" -> 46
        "S" -> 47
        "T" -> 48
        "U" -> 49
        "V" -> 50
        "W" -> 51
        "X" -> 52
        "Y" -> 53
        "Z" -> 54
        else -> 0
    }
}
