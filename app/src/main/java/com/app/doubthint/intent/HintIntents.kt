package com.app.doubthint.intent

sealed interface HintIntent {
    data class SubmitQuestion(val question: String) : HintIntent
    data object RequestNextHint : HintIntent
    data object RequestSolution : HintIntent
    data object GenerateSimilarQuestions : HintIntent
    data object RetryNetwork : HintIntent
}

