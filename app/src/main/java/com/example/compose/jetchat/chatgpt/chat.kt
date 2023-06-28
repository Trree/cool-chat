package com.example.compose.jetchat.chatgpt

import android.util.Log
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI

var MAX_WORD_NUM = 3800
suspend fun getChatResult(contents: List<String>) : String {
    var result = ""
    for (content in contents) {
        if (result.length + content.length + 1 <= MAX_WORD_NUM) {
            if (result.isNotEmpty()) result += ", "
            result += content
        } else {
            break
        }
    }
    return getChatResult(result)
}

@OptIn(BetaOpenAI::class)
suspend fun getChatResult(content : String) : String {
    val openai = OpenAI(
        token = "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",
        logging = LoggingConfig(LogLevel.All)
    )
    val chatCompletionRequest = ChatCompletionRequest(
        model = ModelId("gpt-3.5-turbo"),
        messages = listOf(
            ChatMessage(
                role = ChatRole.System,
                content = "You are a helpful assistant!"
            ),
            ChatMessage(
                role = ChatRole.User,
                content = content
            )
        )
    )

    val result = try {
        val completion: ChatCompletion =
            openai.chatCompletion(chatCompletionRequest)
        completion.choices[0].message?.content ?: ""
    }catch (e : Exception) {
        Log.e("jet-chat", e.toString())
        ""
    }

    return result
}