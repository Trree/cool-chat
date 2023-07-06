package com.example.compose.coolchat.chatgpt

import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.completion.TextCompletion
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIHost

const val MAX_TOKEN = 2000
const val COMPLETION_MODEL = "text-davinci-003"

suspend fun getCompletionResult(content : String) : String {
    val myOpenAiHost = OpenAIHost(baseUrl = LLM_HOST)
    val openai = OpenAI(
        token = OPENAI_API_KEY,
        host = myOpenAiHost,
        logging = LoggingConfig(LogLevel.All)
    )
    val completionRequest = CompletionRequest(
        model = ModelId(COMPLETION_MODEL),
        prompt = "Summarize this for a second-grade student:\r\n$content",
        maxTokens = MAX_TOKEN
    )
    val completion: TextCompletion = openai.completion(completionRequest)
    return completion.choices[0].text
}