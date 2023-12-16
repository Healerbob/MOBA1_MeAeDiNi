package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class HUD {

    private val dice = Dice()
    private val gameManager = GameManager()

    @Composable
    @Preview
    fun BottomLeftComposable() {
        var diceResult by remember { mutableStateOf(0) }
        val gameManager = GameManager()
        var playerName by remember { mutableStateOf(gameManager.gibNaechstenSpielerNamen(2)) }




        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 1.dp)// Optional: Fügen Sie Padding hinzu, um Abstand zum Rand zu haben
        ) {

            Column {
                Text(
                    text = "$playerName am Zug",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )

                Row {
                    Button(
                        onClick = {
                            // Würfeln und das Ergebnis speichern

                            diceResult = dice.roll()
                            playerName = gameManager.gibNaechstenSpielerNamen(2)


                        }
                    ) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Würfel")
                        Text("   $diceResult")
                    }
                }
            }
        }
    }
}
