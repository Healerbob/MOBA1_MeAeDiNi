package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    // Zustand, um zu verfolgen, ob der Startbildschirm angezeigt werden soll
    private var showStartScreen by mutableStateOf(true)
    private var playerCount by mutableStateOf(2)

    private val gameBoard by viewModels<GameBoard>()

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
                            gameBoard.playerCount = it
                        })
                    } else {
                        GameBoardContent(gameBoard)
                    }
                }
            }
        }
    }

    @Composable
    fun StartScreen(onStartButtonClick: (Int) -> Unit) {
        // Lokale Variable, um den Zustand der Anzahl der Spieler zu verfolgen
        var currentPlayerCount by remember { mutableStateOf(playerCount) }

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
                    if (currentPlayerCount > 2) {
                        // Sie können hier eine untere Grenze für die Anzahl der Spieler festlegen
                        currentPlayerCount--
                    }
                }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Remove Player")
                }
                Text(text = "${currentPlayerCount}", style = typography.titleSmall)
                IconButton(onClick = {
                    if (currentPlayerCount < 4) {
                        // Sie können hier eine obere Grenze für die Anzahl der Spieler festlegen
                        currentPlayerCount++
                    }
                }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Add Player")
                }
                // Verwenden Sie den Button aus dem lokalen MaterialTheme
                Button(
                    onClick = {
                        onStartButtonClick(currentPlayerCount)
                    },
                    modifier = Modifier
                        .height(48.dp)
                ) {
                    Text("Start")
                }
            }
        }
    }

    @Composable
    @Preview
    fun GameBoardContent(model: GameBoard = viewModel()) {
        var diceResult by remember { mutableStateOf(1) }

        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            LazyVerticalGrid (
                columns = GridCells.Fixed(model.boardWidth),
                modifier = Modifier
                    .border(2.dp, Color.Black)
                    .background(Color(255, 255, 180))
                    .padding(20.dp)
            ) {
                items(model.fields, key = { it.hashCode() }) { field ->
                    FieldRender(field) { clickedField ->
                        Log.d("CellClicked", "Cell clicked: ${clickedField.id}")
                        if (clickedField.clickable) {
                            //movePawn(clickedField, diceResult)
                            model.nextPlayer()
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 1.dp)// Optional: Fügen Sie Padding hinzu, um Abstand zum Rand zu haben
        ) {
            Column {
                Text(
                    text = "${model.currentPlayer.name} am Zug",
                    style = typography.bodyLarge,
                    color = Color.White
                )
                Row {
                    Button(
                        onClick = {
                            if (!model.moving) {
                                // Würfeln und das Ergebnis speichern
                                diceResult = (1..6).random()
                                model.initiateTurn(diceResult)
                                model.nextPlayer()
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Würfel")
                        Text("   $diceResult")
                    }
                }
            }
        }
    }

    @Composable
    fun FieldRender(value: Field, onCellClicked: (Field) -> Unit) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(32.dp)
        ) {
            if ("FF" != value.id) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .border(1.dp, Color.Black, CircleShape)
                        .background(getCellColor(value.id), CircleShape)
                        .clickable {
                            onCellClicked(value)
                        }
                ) {
                    if ("" == value.occupied) {
                        Text(
                            text = getCellSymbol(value.id),
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(0.8f)
                                .border(1.dp, Color.Black, CircleShape)
                                .background(getPieceColor(value.occupied), CircleShape)
                        )
                    }
                }
            }
        }
    }

    private fun getPieceColor(player: String): Color {
        return when(player) {
            "r" -> Color.Red
            "b" -> Color.Blue
            "g" -> Color.Green
            "y" -> Color.Yellow
            else -> Color.White
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







