package com.example.compose.jetchat.chatgpt

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import com.example.compose.jetchat.conversation.Message
import kotlin.streams.toList

var MAX_WORD_NUM = 3800


@RequiresApi(Build.VERSION_CODES.N)
@OptIn(BetaOpenAI::class)
suspend fun getChatResult(messages: List<Message>) : String {

    val messageHis = messages.stream()
        .map{message -> ChatMessage(message.role, message.content)}
        .toList().reversed()

    return getChatResultByChat(messageHis)
}

@OptIn(BetaOpenAI::class)
suspend fun getChatResultByChat(messages : List<ChatMessage>) : String {
    val openai = OpenAI(
        token = "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",
        logging = LoggingConfig(LogLevel.All)
    )
    val chatCompletionRequest = ChatCompletionRequest(
        model = ModelId("gpt-3.5-turbo"),
        messages = messages
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