package com.app.doubthint.state

data class HintUiState(
    val question: String = "",
    val currentHintLevel: Int = 0,
    val concept: String? = null,
    val hintText: String? = null,
    val formula: String? = null,
    val solution: String? = null,
    val conceptExplanation: String? = null,
    val commonMistakes: String? = null,
    val similarQuestions: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

