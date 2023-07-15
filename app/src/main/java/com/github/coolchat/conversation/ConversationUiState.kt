/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.coolchat.conversation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.toMutableStateList
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatRole
import com.github.coolchat.R

class ConversationUiState(
    val channelName: String,
    initialMessages: List<Message>
) {
    private val _messages: MutableList<Message> = initialMessages.toMutableStateList()
    val messages: List<Message> = _messages

    fun addMessage(msg: Message) {
        _messages.add(0, msg) // Add to the beginning of the list
    }
}

@OptIn(BetaOpenAI::class)
data class Message(
    val name: String,
    val role: ChatRole,
    val content: String,
    val timestamp: String,
    val image: Int? = null,
    val authorImage: Int = if (name == "user") R.drawable.user else R.drawable.assistant
) {
    constructor(message: Message) : this(
        message.name,
        message.role,
        message.content,
        message.timestamp,
        message.image,
        message.authorImage
    )
}

//@Immutable
//data class Message @OptIn(BetaOpenAI::class) constructor(
//    val name: String,
//    val role: ChatRole,
//    val content: String,
//    val timestamp: String,
//    val image: Int? = null,
//    val authorImage: Int = if (name == "user") R.drawable.user else R.drawable.assistant
//)
