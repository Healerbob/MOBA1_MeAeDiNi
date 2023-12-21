package com.example.myapplication

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class Field(val id: String, var occupied: String, var clickable: Boolean, val nextField: HashMap<String, String>, var backgroundColor: Color) {
    val backgroundColorState = mutableStateOf(backgroundColor)
}

class FieldMapper {
    companion object {
        private val fieldLayout = arrayOf(
            arrayOf("r1", "r2", "FF", "FF", "08", "09", "10", "FF", "FF", "b1", "b2"),
            arrayOf("r3", "r4", "FF", "FF", "07", "ba", "11", "FF", "FF", "b3", "b4"),
            arrayOf("FF", "FF", "FF", "FF", "06", "bb", "12", "FF", "FF", "FF", "FF"),
            arrayOf("FF", "FF", "FF", "FF", "05", "bc", "13", "FF", "FF", "FF", "FF"),
            arrayOf("00", "01", "02", "03", "04", "bd", "14", "15", "16", "17", "18"),
            arrayOf("39", "ra", "rb", "rc", "rd", "FF", "gd", "gc", "gb", "ga", "19"),
            arrayOf("38", "37", "36", "35", "34", "yd", "24", "23", "22", "21", "20"),
            arrayOf("FF", "FF", "FF", "FF", "33", "yc", "25", "FF", "FF", "FF", "FF"),
            arrayOf("FF", "FF", "FF", "FF", "32", "yb", "26", "FF", "FF", "FF", "FF"),
            arrayOf("y1", "y2", "FF", "FF", "31", "ya", "27", "FF", "FF", "g1", "g2"),
            arrayOf("y3", "y4", "FF", "FF", "30", "29", "28", "FF", "FF", "g3", "g4"),
        )


        val fields = mutableListOf<Array<Field>>()
        fun createFields(): Array<Array<Field>> {

            fieldLayout.forEach { row ->
                val rowFields = mutableListOf<Field>()
                row.forEach { fieldId ->
                    val backgroundColor =
                        determineBackgroundColor(fieldId) // Funktion zur Bestimmung der Hintergrundfarbe
                    rowFields.add(Field(fieldId, "", false, findNext(fieldId), backgroundColor))
                }
                fields.add(rowFields.toTypedArray())
            }
            return fields.toTypedArray()
        }

        private fun determineBackgroundColor(fieldId: String): Color {
            val flattenedFields = fields.flatMap { it.asIterable() }
            val field = flattenedFields.find { it.id == fieldId }
            return field?.backgroundColorState?.value ?: getDefaultColor(fieldId)
        }


        private fun getDefaultColor(fieldId: String): Color {
            return when {
                "r.".toRegex().matches(fieldId) -> Color.Red
                "b.".toRegex().matches(fieldId) -> Color.Blue
                "g.".toRegex().matches(fieldId) -> Color.Green
                "y.".toRegex().matches(fieldId) -> Color.Yellow
                else -> Color.White // Standardfarbe, wenn keine der Bedingungen erfüllt ist
            }
        }

        private fun findNext(fieldId: String): HashMap<String, String> {
            if ("FF" == fieldId || "\\D\\d".toRegex().matches(fieldId)) {
                return hashMapOf()
            }

            if ("\\D{2}".toRegex().matches(fieldId)) {
                val firstChar = fieldId[0]
                return when (val secondChar = fieldId[1]) {
                    'a' -> hashMapOf(Pair("$firstChar", "${secondChar}b"))
                    'b' -> hashMapOf(Pair("$firstChar", "${secondChar}c"))
                    'c' -> hashMapOf(Pair("$firstChar", "${secondChar}d"))
                    else -> hashMapOf()
                }
            }

            return when (fieldId) {
                "09" -> hashMapOf(Pair("b", "ba"), Pair("", "10"))
                "19" -> hashMapOf(Pair("g", "ga"), Pair("", "20"))
                "29" -> hashMapOf(Pair("y", "ya"), Pair("", "30"))
                "39" -> hashMapOf(Pair("r", "ra"), Pair("", "00"))
                else -> hashMapOf(Pair("", "${fieldId.toInt() + 1}".padStart(2, '0')))
            }
        }

        fun setBackgroundColor(newColor: Color) {
            var backgroundColor = newColor
        }

    }
}