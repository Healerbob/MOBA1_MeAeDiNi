package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class GameBoardRenderer {

    @Composable
    @Preview
    fun getSpielfeldFromGameBoard() {
        val gameBoard = GameBoard()
        val spielfeld = gameBoard.getSpielfeld()
        // Use the spielfeld variable here
        GridDemo(gridArray = spielfeld)
    }

    @Composable
    fun GridDemo(gridArray: Array<Array<String>>) {
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
        ) {
            LazyColumn (
                modifier = Modifier
                    .border(2.dp, Color.Black)
                    .background(Color(255, 255, 180))
                    .padding(20.dp)
            ) {
                items(gridArray.size) { rowIndex ->
                    Row {
                        gridArray[rowIndex].forEach { value ->
                            GridCell(value) { clickedValue ->
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