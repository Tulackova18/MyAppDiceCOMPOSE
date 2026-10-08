package com.example.myapplicationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplicationcompose.ui.theme.MyApplicationCOMPOSETheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
            setContent {
                MaterialTheme {
                    DiceApp()
                }
            }
        }
    }


@Composable
fun DiceApp() {
    val diceSymbols = listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")

    var diceValue by remember { mutableStateOf(1) }
    var isRolling by remember { mutableStateOf(false) }

    val history = remember { mutableStateListOf<Int>() }
    val statistics = remember { mutableStateListOf(0, 0, 0, 0, 0, 0) }

    val scope = rememberCoroutineScope()

    val backgroundColor = Color(0xFFF5F3FF)
    val primaryColor = Color(0xFF352060)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .safeDrawingPadding()
            .padding(27.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hoď kostkou",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor,
            modifier = Modifier.padding(top = 15.dp)
        )

        Text(
            text = diceSymbols[diceValue - 1],
            fontSize = 110.sp,
            color = primaryColor,
            modifier = Modifier.padding(
                top = 20.dp,
                bottom = 20.dp
            )
        )

        Button(
            enabled = !isRolling,
            onClick = {
                isRolling = true

                scope.launch {
                    repeat(10) {
                        diceValue = (1..6).random()
                        delay(250)
                    }

                    val result = (1..6).random()
                    diceValue = result

                    history.add(result)
                    statistics[result - 1]++

                    isRolling = false
                }
            }
        ) {
            Text(
                text = "Hodit",
                fontSize = 20.sp
            )
        }

        Text(
            text = "Počet hodů: ${history.size}",
            fontSize = 18.sp,
            color = primaryColor,
            modifier = Modifier.padding(top = 24.dp)
        )

        Text(
            text = "Celkový součet: ${history.sum()}",
            fontSize = 18.sp,
            color = primaryColor,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = "Nejvyšší hod: ${history.maxOrNull() ?: 0}",
            fontSize = 18.sp,
            color = primaryColor,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = "Statistika",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor,
            modifier = Modifier.padding(top = 28.dp)
        )

        Text(
            text = """
                1: ${statistics[0]}×
                2: ${statistics[1]}×
                3: ${statistics[2]}×
                4: ${statistics[3]}×
                5: ${statistics[4]}×
                6: ${statistics[5]}×
            """.trimIndent(),
            fontSize = 17.sp,
            color = primaryColor,
            modifier = Modifier.padding(top = 12.dp)
        )

        Text(
            text = "Historie",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor,
            modifier = Modifier.padding(top = 28.dp)
        )

        Text(
            text = if (history.isEmpty()) {
                "Zatím žádné hody"
            } else {
                history.joinToString("   ")
            },
            fontSize = 17.sp,
            color = primaryColor,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )

        Button(
            onClick = {
                history.clear()
                statistics.fill(0)
                diceValue = 1
            },
            modifier = Modifier.padding(top = 14.dp)
        ) {
            Text(
                text = "Vymazat",
                fontSize = 16.sp
            )
        }
    }
}