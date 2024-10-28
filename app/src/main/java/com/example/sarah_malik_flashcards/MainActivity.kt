package com.example.sarah_malik_flashcards

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.sarah_malik_flashcards.ui.theme.Sarah_malik_flashcardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sarah_malik_flashcardsTheme {
                Quizlet()
            }
        }
    }
}

@Composable
fun Quizlet() {
    var currentFlashcard by remember { mutableStateOf(questions.random()) }
    var userAnswer by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope() //snackbar shows up in Quizlet

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp), //padding around entire column
            verticalArrangement = Arrangement.spacedBy(16.dp) // padding in between objects in col
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text( //stateless
                    text = currentFlashcard.q,
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            OutlinedTextField( // displays user input
                value = userAnswer,
                onValueChange = { userAnswer = it }, //passing in function that sets user inout as userAnswer
                label = { Text("Your Answer") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedButton( // can clear user input and check if correct
                onClick = {
                    if (userAnswer == currentFlashcard.a) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Correct!")
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Incorrect! The correct answer is ${currentFlashcard.a}")
                        }
                    }
                    // Load a new random question after submission
                    currentFlashcard = questions.random()
                    userAnswer = "" // reassigns userAnswer to empty string until the user gives new answer
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}

class Flashcard(val q: String, val a: String)

val questions = arrayOf(
    Flashcard("What year did Christopher Columbus first arrive in the Americas?", "1492"),
    Flashcard("What year was the Declaration of Independence signed?", "1776"),
    Flashcard("What year did the French Revolution begin?", "1789"),
    Flashcard("What year was the United States Constitution ratified?", "1788"),
    Flashcard("What year did World War I start?", "1914"),
    Flashcard("What year did the stock market crash, marking the start of the Great Depression?", "1929"),
    Flashcard("What year did World War II end?", "1945"),
    Flashcard("What year was the Berlin Wall built?", "1961"),
    Flashcard("What year was the Civil Rights Act passed in the United States?", "1964"),
    Flashcard("What year did the Soviet Union collapse?", "1991"),
    Flashcard("What year did the Roman Empire fall?", "476"),
    Flashcard("What year did the Magna Carta get signed?", "1215"),
    Flashcard("What year did Neil Armstrong land on the moon?", "1969"),
    Flashcard("What year did Martin Luther post his 95 Theses?", "1517"),
    Flashcard("What year was Nelson Mandela released from prison?", "1990"),
    Flashcard("What year did the American Civil War begin?", "1861"),
    Flashcard("What year did the Battle of Hastings take place?", "1066"),
    Flashcard("What year did India gain independence from Britain?", "1947"),
    Flashcard("What year was the United Nations founded?", "1945"),
    Flashcard("What year was the Gutenberg printing press invented?", "1440")
)

@Preview(showBackground = true)
@Composable
fun PreviewQuizlet() {
    Sarah_malik_flashcardsTheme {
        Quizlet()
    }
}
