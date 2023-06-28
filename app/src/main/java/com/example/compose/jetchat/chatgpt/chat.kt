package com.example.compose.jetchat.chatgpt

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI

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
    val completion: ChatCompletion =
        openai.chatCompletion(chatCompletionRequest)
    return completion.choices[0].message?.content?:""
}