package com.app.doubthint.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.doubthint.intent.HintIntent
import com.app.doubthint.network.NetworkModule
import com.app.doubthint.repository.HintRepository
import com.app.doubthint.state.HintUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HintViewModel(
    private val repository: HintRepository = HintRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HintUiState())
    val uiState: StateFlow<HintUiState> = _uiState.asStateFlow()

    private val maxHintLevel = 6
    private val minQuestionLength = 10

    fun handleIntent(intent: HintIntent) {
        when (intent) {
            is HintIntent.SubmitQuestion -> {
                val trimmed = intent.question.trim()
                val validationError = validateQuestion(trimmed)
                if (validationError != null) {
                    _uiState.value = _uiState.value.copy(errorMessage = validationError)
                    return
                }

                _uiState.value = _uiState.value.copy(
                    question = trimmed,
                    currentHintLevel = 0,
                    concept = null,
                    hintText = null,
                    formula = null,
                    solution = null,
                    conceptExplanation = null,
                    commonMistakes = null,
                    similarQuestions = emptyList(),
                    errorMessage = null
                )
                requestHint()
            }

            HintIntent.RequestNextHint -> {
                requestHint()
            }

            HintIntent.RequestSolution -> {
                requestSolution()
            }

            HintIntent.GenerateSimilarQuestions -> {
                generateSimilarQuestions()
            }

            HintIntent.RetryNetwork -> {
                if (_uiState.value.solution == null) {
                    requestHint()
                } else {
                    requestSolution()
                }
            }
        }
    }

    private fun requestHint() {
        val current = _uiState.value
        if (current.question.isBlank()) return
        val nextLevel = (current.currentHintLevel + 1).coerceAtMost(maxHintLevel)

        viewModelScope.launch {
            _uiState.value = current.copy(
                isLoading = true,
                errorMessage = null,
                currentHintLevel = nextLevel,
                hintText = ""
            )

            repository.streamHint(current.question, nextLevel).collectLatest { result ->
                _uiState.value = result.fold(
                    onSuccess = { response ->
                        val validationError = validateHintResponse(response.concept, response.hint)
                        if (validationError != null) {
                            return@fold _uiState.value.copy(
                                isLoading = false,
                                errorMessage = validationError
                            )
                        }

                        _uiState.value.copy(
                            concept = response.concept.ifBlank { _uiState.value.concept },
                            hintText = response.hint,
                            formula = response.formula.ifBlank { _uiState.value.formula },
                            isLoading = false,
                            errorMessage = null
                        )
                    },
                    onFailure = { error ->
                        _uiState.value.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Unable to reach AI service"
                        )
                    }
                )
            }
        }
    }

    private fun requestSolution() {
        val current = _uiState.value
        if (current.question.isBlank()) return

        viewModelScope.launch {
            _uiState.value = current.copy(isLoading = true, errorMessage = null)
            val result = repository.getSolution(current.question)
            _uiState.value = result.fold(
                onSuccess = { response ->
                    val validationError = validateSolutionResponse(response.stepByStepSolution)
                    if (validationError != null) {
                        return@fold current.copy(
                            isLoading = false,
                            errorMessage = validationError
                        )
                    }
                    current.copy(
                        solution = response.stepByStepSolution,
                        conceptExplanation = response.conceptExplanation,
                        commonMistakes = response.commonMistakes,
                        isLoading = false,
                        errorMessage = null
                    )
                },
                onFailure = { error ->
                    current.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unable to reach AI service"
                    )
                }
            )
        }
    }

    private fun generateSimilarQuestions() {
        val current = _uiState.value
        val concept = current.concept ?: return

        viewModelScope.launch {
            _uiState.value = current.copy(isLoading = true, errorMessage = null)
            val result = repository.getSimilarQuestions(concept)
            _uiState.value = result.fold(
                onSuccess = { response ->
                    if (response.questions.isEmpty()) {
                        return@fold current.copy(
                            isLoading = false,
                            errorMessage = "Unable to reach AI service"
                        )
                    }
                    current.copy(
                        similarQuestions = response.questions,
                        isLoading = false,
                        errorMessage = null
                    )
                },
                onFailure = { error ->
                    current.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unable to reach AI service"
                    )
                }
            )
        }
    }

    private fun validateQuestion(question: String): String? {
        if (question.isBlank()) return "Please enter a question"
        if (question.length < minQuestionLength) return "Question appears incomplete"
        if (!looksLikeMathOrPhysics(question)) {
            return "This application currently supports Physics and Mathematics questions only."
        }
        return null
    }

    private fun looksLikeMathOrPhysics(question: String): Boolean {
        val q = question.lowercase()
        val hasNumbersOrSymbols = q.any { it.isDigit() } || listOf("=", "+", "-", "*", "/", "^").any(q::contains)
        val physicsKeywords = listOf("velocity", "acceleration", "force", "work", "power", "energy", "mass", "charge", "current", "voltage", "resistance", "gravity", "momentum", "torque", "pressure", "friction")
        val mathKeywords = listOf("integral", "differentiate", "derivative", "limit", "matrix", "vector", "probability", "equation", "solve", "triangle", "circle", "area", "volume", "log", "sin", "cos", "tan")
        return hasNumbersOrSymbols || physicsKeywords.any(q::contains) || mathKeywords.any(q::contains)
    }

    private fun validateHintResponse(concept: String?, hint: String?): String? {
        if (concept.isNullOrBlank()) return "Unable to reach AI service"
        if (hint.isNullOrBlank()) return "Unable to reach AI service"
        return null
    }

    private fun validateSolutionResponse(stepByStep: String?): String? {
        if (stepByStep.isNullOrBlank()) return "Unable to reach AI service"
        return null
    }
}

