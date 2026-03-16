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
            onValueChange = setQuestion,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter your Physics or Mathematics question") },
            singleLine = false
        )
        Button(
            onClick = {
                if (question.isNotBlank()) {
                    onIntent(HintIntent.SubmitQuestion(question.trim()))
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Analyze Question")
        }
    }
}

