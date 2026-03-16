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
import kotlinx.coroutines.launch

class HintViewModel(
    private val repository: HintRepository = HintRepository(NetworkModule.aiApiService)
) : ViewModel() {

    private val _uiState = MutableStateFlow(HintUiState())
    val uiState: StateFlow<HintUiState> = _uiState.asStateFlow()

    private val maxHintLevel = 6

    fun handleIntent(intent: HintIntent) {
        when (intent) {
            is HintIntent.SubmitQuestion -> {
                _uiState.value = _uiState.value.copy(
                    question = intent.question,
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
            _uiState.value = current.copy(isLoading = true, errorMessage = null)
            val result = repository.getHint(current.question, nextLevel)
            _uiState.value = result.fold(
                onSuccess = { response ->
                    current.copy(
                        currentHintLevel = nextLevel,
                        concept = response.concept,
                        hintText = response.hint,
                        formula = response.formula,
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

    private fun requestSolution() {
        val current = _uiState.value
        if (current.question.isBlank()) return

        viewModelScope.launch {
            _uiState.value = current.copy(isLoading = true, errorMessage = null)
            val result = repository.getSolution(current.question)
            _uiState.value = result.fold(
                onSuccess = { response ->
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
}

