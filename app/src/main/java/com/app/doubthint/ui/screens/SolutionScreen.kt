package com.app.doubthint.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.doubthint.intent.HintIntent
import com.app.doubthint.state.HintUiState

@Composable
fun SolutionScreen(
    state: HintUiState,
    onIntent: (HintIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Solution", modifier = Modifier.fillMaxWidth())

        state.solution?.let { Text(it) }
        state.conceptExplanation?.let { Text("Concept: $it") }
        state.commonMistakes?.let { Text("Common mistakes: $it") }

        Button(
            onClick = { onIntent(HintIntent.GenerateSimilarQuestions) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Practice Similar Questions")
        }
    }
}

