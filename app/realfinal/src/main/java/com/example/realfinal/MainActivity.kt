package com.example.realfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                ExpenseApp()
            }
        }
    }
}

data class Expense(
    val title: String,
    val amount: Int
)

@Composable
fun ExpenseApp() {

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var expenses by remember { mutableStateOf(listOf<Expense>()) }

    val total = expenses.sumOf { it.amount }

    val glitterBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF6A1B9A),
            Color(0xFF8E24AA),
            Color(0xFFCE93D8)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(glitterBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White.copy(alpha = 0.9f),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "미니 가계부",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("소비 항목") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("금액") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val money = amount.toIntOrNull()
                        if (title.isNotBlank() && money != null) {
                            expenses = expenses + Expense(title, money)
                            title = ""
                            amount = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("추가")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "오늘 총 지출: ${total}원",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        expenses = emptyList()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7B1FA2)
                    )
                ) {
                    Text("오늘 지출 초기화")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Divider()

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(expenses) { expense ->
                        Text("• ${expense.title} - ${expense.amount}원")
                    }
                }
            }
        }
    }
}

