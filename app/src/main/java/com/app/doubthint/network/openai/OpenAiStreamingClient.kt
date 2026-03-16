package com.app.doubthint.network.openai

import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSource

class OpenAiStreamingClient(
    private val okHttpClient: OkHttpClient,
    private val apiKey: String,
    private val gson: Gson = Gson()
) {
    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    fun streamResponsesApiText(
        model: String,
        input: String
    ): Sequence<String> {
        val url = "https://api.openai.com/v1/responses"
        val bodyJson = JsonObject().apply {
            addProperty("model", model)
            addProperty("stream", true)
            addProperty("input", input)
        }

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .post(gson.toJson(bodyJson).toRequestBody(jsonMediaType))
            .build()

        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            val body = response.body?.string()
            response.close()
            throw OpenAiHttpException(response.code, url, body)
        }

        val source = response.body?.source() ?: run {
            response.close()
            throw IllegalStateException("Empty response body")
        }

        return safeStream(response, source)
    }

    private fun safeStream(response: okhttp3.Response, source: BufferedSource): Sequence<String> = sequence {
        response.use {
            for (payload in StreamingSseParser.sequence(source)) {
                // Best-effort JSON parsing. Skip malformed events.
                val delta = runCatching { extractTextDelta(payload) }.getOrNull()
                if (!delta.isNullOrEmpty()) yield(delta)
            }
        }
    }

    /**
     * Extracts incremental text from OpenAI Responses API stream events.
     * Supports multiple possible shapes to be resilient across API updates.
     */
    private fun extractTextDelta(payload: String): String? {
        val obj = gson.fromJson(payload, JsonObject::class.java) ?: return null

        // Common event: {"type":"response.output_text.delta","delta":"..."}
        obj.get("delta")?.takeIf { it.isJsonPrimitive }?.let { return it.asString }

        // Some events include {"type":"response.output_text","text":"..."}
        obj.get("text")?.takeIf { it.isJsonPrimitive }?.let { return it.asString }

        // Fallback: nested output_text field
        obj.getAsJsonObject("output_text")
            ?.get("delta")
            ?.takeIf { it.isJsonPrimitive }
            ?.let { return it.asString }

        return null
    }
}

