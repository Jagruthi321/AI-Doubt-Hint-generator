package com.app.doubthint.repository

import com.app.doubthint.model.HintRequest
import com.app.doubthint.model.HintResponse
import com.app.doubthint.model.SimilarQuestionRequest
import com.app.doubthint.model.SimilarQuestionResponse
import com.app.doubthint.model.SolutionRequest
import com.app.doubthint.model.SolutionResponse
import com.app.doubthint.network.AiApiService
import kotlinx.coroutines.CancellationException

class HintRepository(
    private val api: AiApiService
    ) {

    suspend fun getHint(question: String, hintLevel: Int): Result<HintResponse> =
        safeCall {
            api.generateHint(HintRequest(question = question, hintLevel = hintLevel))
        }

    suspend fun getSolution(question: String): Result<SolutionResponse> =
        safeCall {
            api.generateSolution(SolutionRequest(question = question))
        }

    suspend fun getSimilarQuestions(concept: String): Result<SimilarQuestionResponse> =
        safeCall {
            api.generateSimilarQuestions(SimilarQuestionRequest(concept = concept))
        }

    private inline fun <T> safeCall(block: () -> T): Result<T> =
        try {
            Result.success(block())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
}

