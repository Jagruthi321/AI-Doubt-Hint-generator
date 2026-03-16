package com.app.doubthint.network.openai

class OpenAiHttpException(
    val code: Int,
    val url: String,
    val responseBody: String?
) : IllegalStateException("HTTP $code calling $url")

