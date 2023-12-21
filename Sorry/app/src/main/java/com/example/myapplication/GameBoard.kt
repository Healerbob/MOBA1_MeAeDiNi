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

    var firstRun = true


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
        if (firstRun) {
            initializeGameLogic()
            firstRun = false
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
                        .background(
                            field.backgroundColor,
                            CircleShape
                        )  // Verwende die Hintergrundfarbe aus dem Field-Objekt
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
        var playerName by remember { mutableStateOf(currentPlayer) }




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

    fun initializeGameLogic() {
        var players = listOf("r", "b", "g", "y")
        players.forEach { currentPlayer ->
            for (i in 1..4) {
                val player = players[(players.indexOf(currentPlayer) + 1) % players.size]
                occupieField(currentPlayer, "$player$i")
            }
            println(getOccupiedFields(currentPlayer))
        }
    }


    fun evaluateMove(currentPlayer: String, diceResult: Int) {

        if (diceResult <= 6) {
            getOccupiedFields(currentPlayer)
            if (!hasFigureAtHome(currentPlayer)) {
                if (isStartOccupied(currentPlayer)) {
                    getNextField("01", diceResult)
                    highlightPossibleMoves(currentPlayer, diceResult)
                }

            }


            if (isStartOccupied(currentPlayer)) {
                println("Start ist besetzt")
                println(getOccupiedFields(currentPlayer))
            }

            // Beispiel: Ändere die Hintergrundfarbe des Fields mit der ID "00" auf Color.Red
            fields.flatten().find { it.id == getPlayerStart(currentPlayer) }?.backgroundColor =
                getPlayerColor(currentPlayer)
            occupieField(currentPlayer, getPlayerStart(currentPlayer))

            println("Klick")

            updateBoard()
        }
    }

    fun getPlayerColor(currentPlayer: String): Color {
        return when (currentPlayer) {
            "r" -> Color.Red
            "b" -> Color.Blue
            "g" -> Color.Green
            "y" -> Color.Yellow
            else -> Color.White
        }
    }


    fun getPlayerStart(currentPlayer: String): String {
        return when (currentPlayer) {
            "r" -> "00"
            "b" -> "10"
            "g" -> "20"
            "y" -> "30"
            else -> ""
        }
    }

    fun isStartOccupied(currentPlayer: String): Boolean {
        return when (currentPlayer) {
            "r" -> fields.flatten().find { it.id == getPlayerStart(currentPlayer) }?.occupied == "r"
            "b" -> fields.flatten().find { it.id == getPlayerStart(currentPlayer) }?.occupied == "b"
            "g" -> fields.flatten().find { it.id == getPlayerStart(currentPlayer) }?.occupied == "g"
            "y" -> fields.flatten().find { it.id == getPlayerStart(currentPlayer) }?.occupied == "y"
            else -> false
        }
    }

    fun getOccupiedFields(currentPlayer: String): List<String> {
        return when (currentPlayer) {
            "r" -> fields.flatten().filter { it.occupied == "r" }.map { it.id }
            "b" -> fields.flatten().filter { it.occupied == "b" }.map { it.id }
            "g" -> fields.flatten().filter { it.occupied == "g" }.map { it.id }
            "y" -> fields.flatten().filter { it.occupied == "y" }.map { it.id }
            else -> listOf()
        }
    }

    fun occupieField(currentPlayer: String, fieldId: String) {
        fields.flatten().find { it.id == fieldId }?.occupied = currentPlayer
    }

    fun hasFigureAtHome(currentPlayer: String): Boolean {
        when (getOccupiedFields(currentPlayer)) {
            listOf(
                "${currentPlayer}1",
                "${currentPlayer}2",
                "${currentPlayer}3",
                "${currentPlayer}4"
            ) -> {
                println("$currentPlayer hat alle Figuren im Heimfeld")
                return true
            }

            else -> {
                println("$currentPlayer hat noch nicht alle Figuren im Heimfeld")
                return false
            }


        }
    }

    fun moveOutOfStart(currentPlayer: String) {
        fields.flatten().find { it.id == getPlayerStart(currentPlayer) }?.backgroundColor =
            getPlayerColor(currentPlayer)
    }

    fun move(currentPlayer: String, diceResult: Int) {

    }

    fun highlightPossibleMoves(currentPlayer: String, diceResult: Int) {
        val possibleFields = fields.flatten()
            .filter { it.occupied == currentPlayer }
            .mapNotNull { it.id.toIntOrNull()?.let { num -> (num + diceResult).toString() } }

        val felder = fields
        felder.flatten().forEach { feld ->
            if (possibleFields.contains(feld.id)) {
                // Hervorhebung für mögliche Felder
                felder.flatten().find { it.id == feld.id }?.backgroundColor = Color.Magenta
                println("Field: ${feld.id}")
                updateBoard()
            } else {
                // Zurücksetzen der Hervorhebung für andere Felder

            }
        }


    }

    fun getNextField(fieldId: String, diceResult: Int) {
        var currentFieldId = fieldId

        for (i in 0 until diceResult) {
            val currentField = fields.flatten().find { it.id == currentFieldId }

            if (currentField == null) {
                println("Fehler: Das aktuelle Feld wurde nicht gefunden.")
                return
            }

            val nextFieldId = currentField.nextField[currentFieldId]
            println(nextFieldId)

            if (nextFieldId == null) {
                println("Fehler: Das nächste Feld wurde nicht gefunden.")
                return
            }

            currentFieldId = nextFieldId
        }

        println("nextField: $currentFieldId")
    }

}