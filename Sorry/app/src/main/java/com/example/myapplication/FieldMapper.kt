package com.example.myapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class Field(val id: String, var occupied: String, var clickable: Boolean, val nextField: HashMap<String, String>)

class FieldMapper {
    companion object {
        private val fieldLayout = listOf(
            "r1", "r2", "FF", "FF", "08", "09", "10", "FF", "FF", "b1", "b2",
            "r3", "r4", "FF", "FF", "07", "ba", "11", "FF", "FF", "b3", "b4",
            "FF", "FF", "FF", "FF", "06", "bb", "12", "FF", "FF", "FF", "FF",
            "FF", "FF", "FF", "FF", "05", "bc", "13", "FF", "FF", "FF", "FF",
            "00", "01", "02", "03", "04", "bd", "14", "15", "16", "17", "18",
            "39", "ra", "rb", "rc", "rd", "FF", "gd", "gc", "gb", "ga", "19",
            "38", "37", "36", "35", "34", "yd", "24", "23", "22", "21", "20",
            "FF", "FF", "FF", "FF", "33", "yc", "25", "FF", "FF", "FF", "FF",
            "FF", "FF", "FF", "FF", "32", "yb", "26", "FF", "FF", "FF", "FF",
            "y1", "y2", "FF", "FF", "31", "ya", "27", "FF", "FF", "g1", "g2",
            "y3", "y4", "FF", "FF", "30", "29", "28", "FF", "FF", "g3", "g4",
        )

        fun createFields(): SnapshotStateList<Field> {
            val fields = mutableStateListOf<Field>()
            fieldLayout.forEach { fieldId ->
                fields.add(Field(fieldId, checkOccupied(fieldId), false, findNext(fieldId)))
            }
            return fields
        }

        private fun checkOccupied(fieldId: String): String {
            if ("\\D\\d".toRegex().matches(fieldId)) {
                return "${fieldId[0]}"
            }
            return ""
        }

        private fun findNext(fieldId: String): HashMap<String, String> {
            if ("FF" == fieldId || "\\D\\d".toRegex().matches(fieldId)) {
                return hashMapOf()
            }

            if ("\\D{2}".toRegex().matches(fieldId)) {
                val color = fieldId[0]
                return when(fieldId[1]) {
                    'a' -> hashMapOf(Pair("$color", "${color}b"))
                    'b' -> hashMapOf(Pair("$color", "${color}c"))
                    'c' -> hashMapOf(Pair("$color", "${color}d"))
                    else -> hashMapOf()
                }
            }

            return when(fieldId) {
                "09" -> hashMapOf(Pair("b", "ba"), Pair("", "10"))
                "19" -> hashMapOf(Pair("g", "ga"), Pair("", "20"))
                "29" -> hashMapOf(Pair("y", "ya"), Pair("", "30"))
                "39" -> hashMapOf(Pair("r", "ra"), Pair("", "00"))
                else -> hashMapOf(Pair("", "${fieldId.toInt()+1}".padStart(2, '0')))
            }
        }

        fun findStart(color: String): String {
            return when(color) {
                "r" -> "00"
                "b" -> "10"
                "g" -> "20"
                "y" -> "30"
                else -> ""
            }
        }
    }
}