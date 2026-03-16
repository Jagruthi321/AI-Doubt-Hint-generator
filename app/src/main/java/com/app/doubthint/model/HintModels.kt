package com.app.doubthint.model

data class HintRequest(
    val question: String,
    val hintLevel: Int
)

data class HintResponse(
    val concept: String,
    val hint: String,
    val formula: String
)

data class SolutionRequest(
    val question: String
)

data class SolutionResponse(
    val stepByStepSolution: String,
    val conceptExplanation: String,
    val commonMistakes: String
)

data class SimilarQuestionRequest(
    val concept: String
)

data class SimilarQuestionResponse(
    val questions: List<String>
)

