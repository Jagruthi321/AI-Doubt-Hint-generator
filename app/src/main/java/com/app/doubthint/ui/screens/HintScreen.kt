package com.app.doubthint.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.doubthint.intent.HintIntent
import com.app.doubthint.state.HintUiState

@Composable
fun HintScreen(
    state: HintUiState,
    onIntent: (HintIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hint ${state.currentHintLevel}",
            textAlign = TextAlign.Center
        )

        if (state.isLoading) {
            CircularProgressIndicator()
            Text("Analyzing question...")
        } else {
            state.concept?.let {
                Text(text = "Concept: $it", modifier = Modifier.fillMaxWidth())
            }
            state.hintText?.let {
                Text(text = it, modifier = Modifier.fillMaxWidth())
            }
            state.formula?.let {
                Text(text = "Formula: $it", modifier = Modifier.fillMaxWidth())
            }
        }

        state.errorMessage?.let {
            Text(
                text = it,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = { onIntent(HintIntent.RequestNextHint) },
            enabled = !state.isLoading && state.currentHintLevel < 6,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next Hint")
        }
        Button(
            onClick = { onIntent(HintIntent.RequestSolution) },
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show Solution")
        }
        Button(
            onClick = { /* Try solving – no intent, just back to thinking */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Try Solving")
        }
    }
}

