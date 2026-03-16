package com.app.doubthint.network.openai

import okio.BufferedSource

/**
 * Minimal SSE parser for OpenAI streaming responses.
 * Reads "data: ..." lines and yields the raw payload string.
 */
object StreamingSseParser {

    fun sequence(source: BufferedSource): Sequence<String> = sequence {
        while (!source.exhausted()) {
            val line = source.readUtf8Line() ?: break
            if (line.isBlank()) continue
            if (!line.startsWith("data:")) continue

            val payload = line.removePrefix("data:").trim()
            if (payload.isBlank()) continue
            if (payload == "[DONE]") break

            yield(payload)
        }
    }
}

