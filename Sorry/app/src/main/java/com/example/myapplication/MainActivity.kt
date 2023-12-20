package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    // Zustand, um zu verfolgen, ob der Startbildschirm angezeigt werden soll
    private var showStartScreen by mutableStateOf(true)

    private var playerCount by mutableStateOf(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Zeige entweder den Startbildschirm oder das Spielfeld basierend auf dem Zustand an
                    if (showStartScreen) {
                        StartScreen(onStartButtonClick = {
                            showStartScreen = false
                            playerCount = it
                        })
                    } else {
                        GameBoardContent()
                    }
                }
            }
        }
    }
    @Composable
    @Preview
    fun GameBoardContent() {
        val gameBoard = GameBoard(playerCount)
        gameBoard.Render()
    }

    @Composable
    fun StartScreen(onStartButtonClick: (Int) -> Unit) {
        // Lokale Variable, um den Zustand der Anzahl der Spieler zu verfolgen
        var currentNumberOfPlayers by remember { mutableStateOf(2) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sorry",
                style = typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = {
                    if (currentNumberOfPlayers > 2) {
                        // Sie können hier eine untere Grenze für die Anzahl der Spieler festlegen
                        currentNumberOfPlayers--
                    }
                }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Remove Player")
                }
                Text(text = "$currentNumberOfPlayers", style = typography.titleSmall)
                IconButton(onClick = {
                    if (currentNumberOfPlayers < 4) {
                        // Sie können hier eine obere Grenze für die Anzahl der Spieler festlegen
                        currentNumberOfPlayers++
                    }
                }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Add Player")
                }
                // Verwenden Sie den Button aus dem lokalen MaterialTheme
                Button(
                    onClick = {
                        onStartButtonClick(currentNumberOfPlayers)
                    },
                    modifier = Modifier
                        .height(48.dp)
                ) {
                    Text("Start")
                }
            }
        }
    }
}







