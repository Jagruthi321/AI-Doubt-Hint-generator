package com.app.doubthint.repository

import com.app.doubthint.model.HintResponse
import com.app.doubthint.network.NetworkModule
import com.app.doubthint.network.openai.OpenAiHttpException
import com.app.doubthint.network.openai.OpenAiStreamingClient
import com.example.ai_doubt_hint_generator.BuildConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.math.min
import kotlin.random.Random

class HintRepository(
    private val streamingClient: OpenAiStreamingClient = OpenAiStreamingClient(
        okHttpClient = NetworkModule.okHttpClient,
        apiKey = BuildConfig.OPENAI_API_KEY
    )
) {

    fun streamHint(question: String, hintLevel: Int): Flow<Result<HintResponse>> = flow {
        emit(Result.success(HintResponse(concept = "", hint = "", formula = "")))

        val prompt = buildHintPrompt(question = question, hintLevel = hintLevel)
        val builder = StringBuilder()
        var lastConcept: String? = null
        var lastFormula: String? = null

        retryWithBackoff {
            for (delta in streamingClient.streamResponsesApiText(model = "gpt-4.1-mini", input = prompt)) {
                builder.append(delta)
                val parsed = parseHintResponseBestEffort(builder.toString())
                if (parsed != null) {
                    lastConcept = parsed.concept.ifBlank { lastConcept }
                    lastFormula = parsed.formula.ifBlank { lastFormula }
                    emit(Result.success(parsed.copy(
                        concept = parsed.concept.ifBlank { lastConcept.orEmpty() },
                        formula = parsed.formula.ifBlank { lastFormula.orEmpty() }
                    )))
                }
            }
        }
    }

    // TODO: Implement streamSolution / streamSimilarQuestions using the same approach.

    private fun buildHintPrompt(question: String, hintLevel: Int): String =
        """
You are a tutor for Physics and Mathematics numerical problems.
Return ONLY valid JSON with keys: concept, hint, formula.

Task: Provide hint level $hintLevel of 6 for the following question.
- Hint 1: concept identification
- Hint 2: ask student what they tried
- Hint 3: relevant formulas
- Hint 4: approach guidance
- Hint 5: partial solution step
- Hint 6: near-complete reasoning without final answer

Question:
$question
        """.trimIndent()

    private fun parseHintResponseBestEffort(text: String): HintResponse? {
        // Best-effort extraction of the first {...} block as JSON.
        val start = text.indexOf('{')
        val end = text.lastIndexOf('}')
        if (start < 0 || end <= start) return null
        val json = text.substring(start, end + 1)
        return runCatching {
            val obj = com.google.gson.JsonParser.parseString(json).asJsonObject
            val concept = obj.get("concept")?.asString ?: ""
            val hint = obj.get("hint")?.asString ?: ""
            val formula = obj.get("formula")?.asString ?: ""
            HintResponse(concept = concept, hint = hint, formula = formula)
        }.getOrNull()
    }

    private suspend fun retryWithBackoff(block: suspend () -> Unit) {
        val maxAttempts = 4
        var attempt = 0
        while (true) {
            try {
                block()
                return
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                attempt++
                val retryable = isRetryable(e)
                if (!retryable || attempt >= maxAttempts) throw e

                val baseMs = 500L * (1L shl (attempt - 1))
                val jitter = Random.nextLong(0, 250)
                delay(min(4000L, baseMs + jitter))
            }
        }
    }

    private fun isRetryable(e: Exception): Boolean = when (e) {
        is UnknownHostException -> true
        is SocketTimeoutException -> true
        is OpenAiHttpException -> e.code == 408 || e.code == 429 || e.code in 500..599
        else -> false
    }
}

