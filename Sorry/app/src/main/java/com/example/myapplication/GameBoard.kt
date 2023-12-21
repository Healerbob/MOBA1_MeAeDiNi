package com.example.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class Player(val color: String, var name: String, var startIndex: Int)

class GameBoard(var playerCount: Int = 2): ViewModel() {
    private val playerColors = listOf("r", "g", "b", "y")

    val boardWidth = 11
    val boardHeight = 11

    val fields: SnapshotStateList<Field>
    val players: List<Player> = playerColors.subList(0, playerCount).map { Player(it, "", -1) }

    var currentPlayer: Player by mutableStateOf(players[0])
    var moving: Boolean by mutableStateOf(false)

    private var reroll = false;

    init {
        fields = FieldMapper.createFields()

        players.forEach { player ->
            val field = fields.find { FieldMapper.findStart(player.color) == it.id }
            player.startIndex = fields.indexOf(field)
            player.name = mapPlayerName(player.color)
        }
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

    fun initiateTurn(diceResult: Int) {
        moving = true

        if (diceResult == 6) {
            reroll = true

            if (isStartAvailable(currentPlayer.color, currentPlayer.startIndex)) {
                goToStart(currentPlayer.color, currentPlayer.startIndex)
                moving = false
            }
        }

        if (moving) {

        }
    }

    fun movePawn(clickedField: String, diceResult: Int) {
        TODO("Not yet implemented")
    }

    fun nextPlayer() {
        currentPlayer = players[(players.indexOf(currentPlayer) + 1) % players.size]
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
            i--
        } while(i > 0)

        if (startField.occupied != "") {
            kickPawn(startField)
        }

        sourceField.occupied = ""
        startField.occupied = color
    }

    private fun kickPawn(field: Field) {
        TODO("Not yet implemented")
    }
}