package com.app.doubthint.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.doubthint.intent.HintIntent

@Composable
fun HomeScreen(
    onIntent: (HintIntent) -> Unit
) {
    val (question, setQuestion) = remember { mutableStateOf("") }
    val (validation, setValidation) = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "AI Doubt Hint Generator",
            textAlign = TextAlign.Center
        )
        OutlinedTextField(
            value = question,
            onValueChange = {
                setQuestion(it)
                if (validation != null) setValidation(null)
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter your Physics or Mathematics question") },
            singleLine = false
        )
        validation?.let {
            Text(text = it, color = Color.Red, modifier = Modifier.fillMaxWidth())
        }
        Button(
            onClick = {
                val trimmed = question.trim()
                if (trimmed.isBlank()) {
                    setValidation("Please enter a question")
                    return@Button
                }
                if (trimmed.length < 10) {
                    setValidation("Question appears incomplete")
                    return@Button
                }
                onIntent(HintIntent.SubmitQuestion(trimmed))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Analyze Question")
        }
    }
}

