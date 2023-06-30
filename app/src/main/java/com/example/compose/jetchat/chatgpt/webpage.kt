package com.example.compose.jetchat.chatgpt

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

fun isUrl(input: String): Boolean {
    val url = input.trim()
    val urlRegex = Regex("^https?://[\\w\\d\\-_]+(\\.[\\w\\d\\-_]+)*(\\S*)$")
    return urlRegex.matches(url)
}

suspend fun getHtmlContent(url: String): String {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .build()

    return withContext(Dispatchers.IO) {
        val response = client.newCall(request).execute()
        response.body?.string() ?: ""
    }
}

suspend fun getWebPageContent(content : String): String {
    val url = content.trim()
    if (!isUrl(url)) {
        return ""
    }
    return getHtmlContent(url)
}

suspend fun getWebPageSummarize(url : String) :String {
    val content = getWebPageContent(url)
    return getCompletionResult(content)
}