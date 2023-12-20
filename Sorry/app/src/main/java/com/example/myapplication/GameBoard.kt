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
import androidx.compose.ui.unit.dp

class GameBoard (playerCount: Int) {
    private val playerColors = listOf("r", "g", "b", "y")

    val fields: Array<Array<Field>>
    val players: List<String> = playerColors.subList(0, playerCount)

    var currentPlayer: String

    var diceResult: Int = 3

    init {
        fields = FieldMapper.createFields()

        currentPlayer = players[0]
    }

    @Composable
    fun Render() {
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
                        fields[rowIndex].forEach { value ->
                            GridCell(value) { clickedField ->
                                Log.d("CellClicked", "Cell clicked: ${clickedField.id}")
                                if (clickedField.clickable) {
                                    movePawn(clickedField, diceResult)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun movePawn(clickedField: Field, diceResult: Int) {
        TODO("Not yet implemented")
    }

    @Composable
    fun GridCell(value: Field, onCellClicked: (Field) -> Unit) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(32.dp)
        ) {
            if ("FF" != value.id) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .border(1.dp, Color.Black, CircleShape)
                        .background(getCellColor(value.id), CircleShape)
                        .clickable {
                            onCellClicked(value)
                        }
                ) {
                    Text(
                        text = getCellSymbol(value.id),
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
            "00" == value || "r.".toRegex().matches(value) -> Color.Red
            "10" == value || "b.".toRegex().matches(value) -> Color.Blue
            "20" == value || "g.".toRegex().matches(value) -> Color.Green
            "30" == value || "y.".toRegex().matches(value) -> Color.Yellow
            else -> Color.White
        }
    }

    @Composable
    fun getCellSymbol(value: String): String {
        return when {
            ".0".toRegex().matches(value) -> "A"
            ".a".toRegex().matches(value) -> "a"
            ".b".toRegex().matches(value) -> "b"
            ".c".toRegex().matches(value) -> "c"
            ".d".toRegex().matches(value) -> "d"
            else -> ""
        }
    }
}