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
import com.aallam.openai.client.OpenAIHost
import com.example.compose.jetchat.conversation.Message
import com.github.michaelbull.retry.policy.binaryExponentialBackoff
import com.github.michaelbull.retry.policy.limitAttempts
import com.github.michaelbull.retry.policy.plus
import com.github.michaelbull.retry.retry

val MAX_TOKEN_NUM = 4097


@RequiresApi(Build.VERSION_CODES.N)
@OptIn(BetaOpenAI::class)
suspend fun getChatResult(messages: List<Message>) : String {

    val useToken = 0
    val messageHis = mutableListOf<ChatMessage>()
    for(msg in messages) {
        //200 作为预估的误差补偿
        val msgTokenSize = getTokenNum(msg.content) +200
        if (msgTokenSize + useToken > MAX_TOKEN_NUM) {
            break
        }
        messageHis.add(ChatMessage(msg.role, msg.content))
    }
    messageHis.reverse()

    return getChatResultByChat(messageHis)
}

@OptIn(BetaOpenAI::class)
suspend fun getChatResultByChat(messages : List<ChatMessage>) : String {
    val myOpenAiHost = OpenAIHost(baseUrl = "https://api.openai.com/v1/")
    val openai = OpenAI(
        token = "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",
        host = myOpenAiHost,
        logging = LoggingConfig(LogLevel.All)
    )
    val chatCompletionRequest = ChatCompletionRequest(
        model = ModelId("gpt-3.5-turbo"),
        messages = messages
    )

    val result = retry(limitAttempts(3) + binaryExponentialBackoff(base = 10L, max = 5000L)) {
        val completion: ChatCompletion =
            openai.chatCompletion(chatCompletionRequest)
        completion.choices[0].message?.content ?: ""
    }

    return result
}