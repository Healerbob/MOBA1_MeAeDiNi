package com.example.myapplication

class GameBoard {
    val menschAergereDichNichtSpielfeld = arrayOf(
        arrayOf("r", "r", "0", "0", "F", "F", "S", "0", "0", "b", "b"),
        arrayOf("r", "r", "0", "0", "F", "Z", "F", "0", "0", "b", "b"),
        arrayOf("0", "0", "0", "0", "F", "Z", "F", "0", "0", "0", "0"),
        arrayOf("0", "0", "0", "0", "F", "Z", "F", "0", "0", "0", "0"),
        arrayOf("S", "F", "F", "F", "F", "Z", "F", "F", "F", "F", "F"),
        arrayOf("F", "Z", "Z", "Z", "Z", "0", "Z", "Z", "Z", "Z", "F"),
        arrayOf("F", "F", "F", "F", "F", "Z", "F", "F", "F", "F", "S"),
        arrayOf("0", "0", "0", "0", "F", "Z", "F", "0", "0", "0", "0"),
        arrayOf("0", "0", "0", "0", "F", "Z", "F", "0", "0", "0", "0"),
        arrayOf("y", "y", "0", "0", "F", "Z", "F", "0", "0", "g", "g"),
        arrayOf("y", "y", "0", "0", "S", "F", "F", "0", "0", "g", "g"),
        arrayOf("0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"),
        arrayOf("0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0")
    )
    fun getSpielfeld() : Array<Array<String>> {
        return menschAergereDichNichtSpielfeld
    }
}