package com.example.myapplication

class GameBoard {
    private val menschAergereDichNichtSpielfeld = arrayOf(
        arrayOf("r0", "r1", "0", "0", "F", "F", "bs", "0", "0", "b0", "b1"),
        arrayOf("r2", "r3", "0", "0", "F", "ba", "F", "0", "0", "b2", "b3"),
        arrayOf("0", "0", "0", "0", "F", "bb", "F", "0", "0", "0", "0"),
        arrayOf("0", "0", "0", "0", "F", "bc", "F", "0", "0", "0", "0"),
        arrayOf("rs", "F", "F", "F", "F", "bd", "F", "F", "F", "F", "F"),
        arrayOf("F", "ra", "rb", "rc", "rd", "0", "gd", "gc", "gb", "ga", "F"),
        arrayOf("F", "F", "F", "F", "F", "yd", "F", "F", "F", "F", "gs"),
        arrayOf("0", "0", "0", "0", "F", "yc", "F", "0", "0", "0", "0"),
        arrayOf("0", "0", "0", "0", "F", "yb", "F", "0", "0", "0", "0"),
        arrayOf("y0", "y1", "0", "0", "F", "ya", "F", "0", "0", "g0", "g1"),
        arrayOf("y2", "y3", "0", "0", "ys", "F", "F", "0", "0", "g2", "g3"),
    )

    fun getSpielfeld(): Array<Array<String>> {
        return menschAergereDichNichtSpielfeld
    }
}