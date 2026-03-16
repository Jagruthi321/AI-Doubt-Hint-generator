package com.app.doubthint.network

import com.app.doubthint.model.HintRequest
import com.app.doubthint.model.HintResponse
import com.app.doubthint.model.SimilarQuestionRequest
import com.app.doubthint.model.SimilarQuestionResponse
import com.app.doubthint.model.SolutionRequest
import com.app.doubthint.model.SolutionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AiApiService {

    @POST("generate-hint")
    suspend fun generateHint(
        @Body request: HintRequest
    ): HintResponse

    @POST("generate-solution")
    suspend fun generateSolution(
        @Body request: SolutionRequest
    ): SolutionResponse

    @POST("generate-similar")
    suspend fun generateSimilarQuestions(
        @Body request: SimilarQuestionRequest
    ): SimilarQuestionResponse
}

