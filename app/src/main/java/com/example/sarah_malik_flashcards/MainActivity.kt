package com.example.sarah_malik_flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashcardsQuizApp()
        }
    }
}

data class Flashcard(val question: String, val answer: String)

val questions = listOf(
    Flashcard("What year did Christopher Columbus first arrive in the Americas?", "1492"),
    Flashcard("What year was the Declaration of Independence signed?", "1776"),
    Flashcard("What year did the French Revolution begin?", "1789"),
    Flashcard("What year was the United States Constitution ratified?", "1788"),
    Flashcard("What year did World War I start?", "1914")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardsQuizApp() {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var userAnswer by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isQuizComplete = currentQuestionIndex >= questions.size

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isQuizComplete) {
                Text(
                    text = "Quiz Complete!",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    currentQuestionIndex = 0
                    userAnswer = ""
                }) {
                    Text("Restart Quiz")
                }
            } else {
                val currentFlashcard = questions[currentQuestionIndex]

                // Question Card
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = currentFlashcard.question,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                // Answer Input
                OutlinedTextField(
                    value = userAnswer,
                    onValueChange = { userAnswer = it },
                    label = { Text("Your Answer") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Submit Button
                Button(
                    onClick = {
                        if (userAnswer.equals(currentFlashcard.answer, ignoreCase = true)) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Correct!")
                            }
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("Incorrect! The correct answer is ${currentFlashcard.answer}")
                            }
                        }

                        // Move to the next question
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                        } else {
                            // Quiz completed
                            currentQuestionIndex++
                        }
                        userAnswer = "" // Clear input field
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit Answer")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFlashcardsQuizApp() {
    FlashcardsQuizApp()
}