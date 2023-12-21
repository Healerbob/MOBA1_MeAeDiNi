package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class GameBoard (playerCount: Int) {
    private val playerColors = listOf("r", "g", "b", "y")

    var fields: Array<Array<Field>>
    val players: List<String> = playerColors.subList(0, playerCount)

    var currentPlayer: String

    var diceResult: Int = 3


    init {
        fields = FieldMapper.createFields()

        currentPlayer = players[0]
    }

    fun updatePlayer() {
        currentPlayer = players[(players.indexOf(currentPlayer) + 1) % players.size]
    }

    var boardUpdate by mutableStateOf(0)

    // Funktion zum Aktualisieren des Spielfelds
    fun updateBoard() {
        boardUpdate += 1
    }


    @Composable
    fun Render() {
        val updatedState = rememberUpdatedState(boardUpdate)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            LazyColumn(
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
    fun GridCell(field: Field, onCellClicked: (Field) -> Unit) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(32.dp)
        ) {
            if ("FF" != field.id) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .border(1.dp, Color.Black, CircleShape)
                        .background(field.backgroundColor, CircleShape)  // Verwende die Hintergrundfarbe aus dem Field-Objekt
                        .clickable {
                            onCellClicked(field)
                        }
                ) {
                    Text(
                        text = getCellSymbol(field.id),
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
             "r.".toRegex().matches(value) -> Color.Red
             "b.".toRegex().matches(value) -> Color.Blue
             "g.".toRegex().matches(value) -> Color.Green
             "y.".toRegex().matches(value) -> Color.Yellow
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


    @Composable
    fun BottomLeftComposable() {
        val dice = Dice()
        var diceResult by remember { mutableStateOf(0) }
        var playerName by remember { mutableStateOf(currentPlayer)}




        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 1.dp)// Optional: Fügen Sie Padding hinzu, um Abstand zum Rand zu haben
        ) {

            Column {
                Text(
                    text = "$playerName am Zug",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )

                Row {
                    Button(
                        onClick = {
                            // Würfeln und das Ergebnis speichern
                            diceResult = dice.roll()
                            evaluateMove(currentPlayer = currentPlayer, diceResult = diceResult)
                            updatePlayer()
                            playerName = currentPlayer


                        }
                    ) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Würfel")
                        Text("   $diceResult")
                    }
                }
            }
        }

    }


    fun evaluateMove(currentPlayer: String, diceResult: Int) {
        if (diceResult <= 6) {
            // Beispiel: Ändere die Hintergrundfarbe des Fields mit der ID "00" auf Color.Red
            fields.flatten().find { it.id == getPlayerStart(currentPlayer) }?.backgroundColor = getPlayerColor(currentPlayer)
            fields.flatten().find { it.id == getPlayerStart(currentPlayer) }?.occupied = currentPlayer

            println("Klick")

            updateBoard()
        }
    }

    fun getPlayerColor(currentPlayer: String): Color {
        return when (currentPlayer) {
            "r" -> Color.Red
            "g" -> Color.Yellow
            "b" -> Color.Blue
            "y" -> Color.Green
            else -> Color.White
        }
    }

    fun getPlayerStart(currentPlayer: String): String {
        return when (currentPlayer) {
            "r" -> "00"
            "g" -> "10"
            "b" -> "20"
            "y" -> "30"
            else -> ""
        }
    }



}