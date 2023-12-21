package com.example.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

data class Player(val id: String, var name: String, var startIndex: Int)

class GameBoardFactory(private val playerCount: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        modelClass.getConstructor(Int::class.java)
            .newInstance(playerCount)
}

class GameBoard(playerCount: Int = 2): ViewModel() {
    private val playerColors = listOf("r", "g", "b", "y")
    private val players: List<Player> = playerColors.subList(0, playerCount).map { Player(it, "", -1) }

    val boardWidth = 11

    val fields: SnapshotStateList<Field> = FieldMapper.createFields()

    var diceResult by mutableStateOf(0)
    var currentPlayer: Player by mutableStateOf(players[0])
    var moving: Boolean by mutableStateOf(false)

    private var reRoll = false

    var finished by mutableStateOf(false)

    init {
        players.forEach { player ->
            val field = fields.find { FieldMapper.findStart(player.id) == it.id }
            player.startIndex = fields.indexOf(field)
            player.name = mapPlayerName(player.id)
        }
    }

    fun rollDice() {
        diceResult = (1..6).random()
    }

    fun initiateTurn() {
        moving = true

        if (diceResult == 6) {
            reRoll = true

            if (isStartAvailable(currentPlayer.id, currentPlayer.startIndex)) {
                goToStart(currentPlayer.id, currentPlayer.startIndex)
                moving = false
            }
        }

        if (moving) {
            var availableFields = 0
            fields.filter { it.nextField.isNotEmpty() }.forEach { field ->
                if (field.occupied == currentPlayer.id) {
                    val targetField = getTargetField(field, currentPlayer.id, diceResult)
                    if (targetField != null && targetField.occupied != currentPlayer.id) {
                        field.clickable = true
                        availableFields++
                    }
                }
            }
            if (availableFields == 0) {
                moving = false
            }
        }
    }

    private fun getTargetField(field: Field, color: String, diceResult: Int): Field? {
        var targetField: Field? = field
        for (i in 1..diceResult) {
            var newId = targetField!!.nextField[color]
            if (newId == null) {
                newId = targetField.nextField[""]
            }

            targetField = fields.find { it.id == newId }
            if (targetField == null) {
                break
            }
        }
        return targetField
    }

    fun movePawn(clickedField: Field) {
        val targetField = getTargetField(clickedField, currentPlayer.id, diceResult)!!
        if (targetField.occupied != "") {
            kickPawn(targetField)
        }
        clickedField.occupied = ""
        targetField.occupied = currentPlayer.id

        fields.forEach {
            it.clickable = false
        }

        moving = false
    }

    private fun mapPlayerName(color: String): String {
        return when(color) {
            "r" -> "Rot"
            "b" -> "Blau"
            "g" -> "Grün"
            "y" -> "Gelb"
            else -> ""
        }
    }

    private fun kickPawn(field: Field) {
        val color = field.occupied

        for (i in 1..4) {
            val homeField = fields.find { it.id == "${color}${i}" }!!
            if (homeField.occupied == "") {
                homeField.occupied = color
                break
            }
        }

        field.occupied = ""
    }

    fun nextPlayer() {
        if (!reRoll) {
            currentPlayer = players[(players.indexOf(currentPlayer) + 1) % players.size]
        } else {
            currentPlayer = players[(players.indexOf(currentPlayer))]
            reRoll = false
        }

        diceResult = 0
        checkVictory(currentPlayer.id)
    }

    private fun isStartAvailable(color: String, fieldIndex: Int): Boolean {
        return fields[fieldIndex].occupied != color
                && fields.find { it.id == "${color}1"}!!.occupied != ""
    }

    private fun goToStart(color: String, fieldIndex: Int) {
        val startField = fields[fieldIndex]

        var sourceField: Field
        var i = 4
        do {
            sourceField = fields.find { it.id == "${color}${i}" }!!
            if (sourceField.occupied != "") {
                i = 0
            } else {
                i--
            }
        } while(i > 0)

        if (startField.occupied != "") {
            kickPawn(startField)
        }

        sourceField.occupied = ""
        startField.occupied = color
    }

    private fun checkVictory(color: String) {
        fields.forEach { field ->
            when(field.id) {
                "${color}a" -> finished = finished && field.occupied != ""
                "${color}b" -> finished = finished && field.occupied != ""
                "${color}c" -> finished = finished && field.occupied != ""
                "${color}d" -> finished = finished && field.occupied != ""
            }
        }
    }
}