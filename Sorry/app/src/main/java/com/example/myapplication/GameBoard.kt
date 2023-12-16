package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class GameBoard {
    private val boardLayout = arrayOf(
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

    val fields = arrayOfNulls<Array<Field?>>(boardLayout.size)
    val players = listOf("r","b","y","g")

    init {
        boardLayout.forEachIndexed { index, row ->
            val rowFields = arrayOfNulls<Field>(row.size)
            for (i in 0..row.lastIndex) {
                rowFields[i] = Field(row[i], "")
            }
            fields[index] = rowFields
        }
    }

    @Preview
    @Composable
    fun render() {
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            LazyColumn (
                modifier = Modifier
                    .border(2.dp, Color.Black)
                    .background(Color(255, 255, 180))
                    .padding(20.dp)
            ) {
                items(fields.size) { rowIndex ->
                    Row {
                        fields[rowIndex]!!.forEach { value ->
                            GridCell(value!!.id) { clickedValue ->
                                Log.d("CellClicked", "Cell clicked: $clickedValue")
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun GridCell(value: String, onCellClicked: (String) -> Unit) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(32.dp)
        ) {
            if ("0" != value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .border(1.dp, Color.Black, CircleShape)
                        .background(getCellColor(value), CircleShape)
                        .clickable {
                            onCellClicked(value)
                        }
                ) {
                    Text(
                        text = getCellSymbol(value),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }

    @Composable
    fun getCellColor(value: String): Color {
        return when {
            "F" == value -> Color.White
            "g.".toRegex().matches(value) -> Color.Green
            "b.".toRegex().matches(value) -> Color.Blue
            "y.".toRegex().matches(value) -> Color.Yellow
            "r.".toRegex().matches(value) -> Color.Red
            else -> MaterialTheme.colorScheme.background
        }
    }

    @Composable
    fun getCellSymbol(value: String): String {
        return when {
            ".s".toRegex().matches(value) -> "A"
            ".a".toRegex().matches(value) -> "a"
            ".b".toRegex().matches(value) -> "b"
            ".c".toRegex().matches(value) -> "c"
            ".d".toRegex().matches(value) -> "d"
            else -> ""
        }
    }
}