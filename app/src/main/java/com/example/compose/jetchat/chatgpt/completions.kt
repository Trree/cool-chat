package com.example.compose.jetchat.chatgpt

import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.completion.TextCompletion
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIHost

suspend fun getCompletionResult(content : String) : String {
    val myOpenAiHost = OpenAIHost(baseUrl = "https://api.openai.com/v1/")
    val openai = OpenAI(
        token = "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",
        host = myOpenAiHost,
        logging = LoggingConfig(LogLevel.All)
    )
    val completionRequest = CompletionRequest(
        model = ModelId("text-davinci-003"),
        prompt = "Summarize this for a second-grade student:\r\n$content",
        //echo = true
    )
    val completion: TextCompletion = openai.completion(completionRequest)
    return completion.choices[0].text
}