package com.example.myapplication

class GameManager {
    private val players = listOf("rot", "gelb", "blau", "grün")
    private var currentPlayerIndex = 0

    fun gibNaechstenSpielerNamen(anzahlSpieler: Int): String {
        require(anzahlSpieler in 1..4) { "Die Anzahl der Spieler muss zwischen 1 und 4 liegen." }

        val spielerName = players[currentPlayerIndex]
        currentPlayerIndex = (currentPlayerIndex + 1) % anzahlSpieler
        return spielerName
    }
}


