package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class GameBoardRenderer {

    @Composable
    fun getSpielfeldFromGameBoard() {
        val gameBoard = GameBoard()
        val spielfeld = gameBoard.getSpielfeld()
        // Use the spielfeld variable here
        GridDemo(gridArray = spielfeld)
    }

    @Composable
    fun GridDemo(gridArray: Array<Array<String>>) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp) // Reduziere das Padding
        ) {
            items(gridArray.size) { rowIndex ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 1.dp),
                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    gridArray[rowIndex].forEach { value ->
                        GridCell(value = value) { clickedValue ->
                            Log.d("CellClicked", "Cell clicked: $clickedValue")                    }
                    }
                }
            }
        }
    }


    @Composable
    fun GridCell(value: String, onCellClicked: (String) -> Unit) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .padding(1.dp)
                .background(getCellColor(value))
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    onCellClicked(value)
                }
        ) {
            val textColor = when (value) {
                "S", "Z", "F" -> Color.Black
                else -> Color.White
            }

            Text(
                text = getCellSymbol(value),
                color = textColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }



    @Composable
    fun getCellColor(value: String): Color {
        return when (value) {
            "S" -> Color(255, 223, 0) // Gold
            "Z" -> Color.Gray
            "F" -> Color.Cyan
            "g" -> Color.Green
            "b" -> Color.Blue
            "y" -> Color.Yellow
            "r" -> Color.Red
            else -> MaterialTheme.colorScheme.background
        }
    }

    @Composable
    fun getCellSymbol(value: String): String {
        return when (value) {
            "S" -> "S"
            "Z" -> "Z"
            "F" -> ""
            "g" -> "g"
            "b" -> "b"
            "y" -> "y"
            "r" -> "r"
            else -> ""
        }
    }

}