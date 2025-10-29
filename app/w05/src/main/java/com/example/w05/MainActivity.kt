package com.example.w05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            yeongohTheme {

                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CounterApp()
                    Spacer(modifier = Modifier.height(32.dp))
                    StopWatchApp()
                }
            }
        }
    }
}

@Composable
fun CounterApp() {}

@Composable
fun StopWatchApp() {}

@Preview(showBackground = true)
@Composable
fun CounterAppPreview() {
    CounterApp()
}

@Preview(showBackground = true)
@Composable
fun StopWatchPreview() {
    StopWatchApp()
}

@Composable
fun CounterApp() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Count: 0")
        Button(onClick = {  }) {
            Text("Increase")
        }
    }
}

@Composable
fun StopWatchApp() {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "00:00")
        Row {
            Button(onClick = { /* 시작 */ }) { Text("Start") }
            Button(onClick = { /* 중지 */ }) { Text("Stop") }
            Button(onClick = { /* 리셋 */ }) { Text("Reset") }
        }
    }
}