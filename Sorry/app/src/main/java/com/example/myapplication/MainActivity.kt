package com.example.myapplication



import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val menschAergereDichNichtSpielfeld = arrayOf(
                        arrayOf("r", "r", "0", "0", "F", "F", "S", "0", "0", "b", "b"),
                        arrayOf("r", "r", "0", "0", "F", "Z", "F", "0", "0", "b", "b"),
                        arrayOf("0", "0", "0", "0", "F", "Z", "F", "0", "0", "0", "0"),
                        arrayOf("0", "0", "0", "0", "F", "Z", "F", "0", "0", "0", "0"),
                        arrayOf("S", "F", "F", "F", "F", "Z", "F", "F", "F", "F", "F"),
                        arrayOf("F", "Z", "Z", "Z", "Z", "0", "Z", "Z", "Z", "Z", "F"),
                        arrayOf("F", "F", "F", "F", "F", "Z", "F", "F", "F", "F", "S"),
                        arrayOf("0", "0", "0", "0", "F", "Z", "F", "0", "0", "0", "0"),
                        arrayOf("0", "0", "0", "0", "F", "Z", "F", "0", "0", "0", "0"),
                        arrayOf("y", "y", "0", "0", "F", "Z", "F", "0", "0", "g", "g"),
                        arrayOf("y", "y", "0", "0", "S", "F", "F", "0", "0", "g", "g"),
                        arrayOf("0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"),
                        arrayOf("0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0")
                    )


                    GridDemo(menschAergereDichNichtSpielfeld)
                }
            }
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var showGreeting2 by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(onClick = { showGreeting2 = true }) {
            Text(text = "Hier für Linux")
        }

        if (showGreeting2) {
            Greeting2(name = "Linux")
        }
    }
}
@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
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






