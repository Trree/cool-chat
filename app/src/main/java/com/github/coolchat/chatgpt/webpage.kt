package com.github.coolchat.chatgpt

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatRole
import com.github.coolchat.conversation.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

@OptIn(BetaOpenAI::class)
suspend fun getWebPageSummarize(url: String): Message {
    val content = getWebPageContent(url)
    val timeFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
    val currentTime = timeFormat.format(Date())
    return Message(ChatRole.Assistant.role, ChatRole.Assistant, content, currentTime)
}