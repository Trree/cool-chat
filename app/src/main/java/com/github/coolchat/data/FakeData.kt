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

package com.github.coolchat.data

import android.content.Context
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatRole.Companion.Assistant
import com.github.coolchat.R
import com.github.coolchat.conversation.ConversationUiState
import com.github.coolchat.conversation.Message
import com.github.coolchat.profile.ProfileScreenState

@OptIn(BetaOpenAI::class)
private val initialMessages = listOf(
    Message(
        Assistant.role,
        Assistant,
        "You are a helpful assistant.",
        "8:07 PM"
    )
)

val exampleUiState = ConversationUiState(
    initialMessages = initialMessages,
    channelName = "chat",
)

/**
 * Example colleague profile
 */
val colleagueProfile = ProfileScreenState(
    userId = "12345",
    photo = R.drawable.someone_else,
    name = "Taylor Brooks",
    displayName = "taylor",
    commonChannels = "2"
)

/**
 * Example "me" profile.
 */
val meProfile = ProfileScreenState(
    userId = "me",
    photo = R.drawable.someone_else,
    name = "Ali Conors",
    displayName = "aliconors",
    commonChannels = null
)

fun runOnce(f: () -> Unit): () -> Unit {
    var executed = false
    return {
        if (!executed) {
            f()
            executed = true
        }
    }
}

fun initPromDataOnce(context: Context) {
    val database by lazy { PromptDatabase.getDatabase(context) }
    try {
        val dao = database.promptDao();
        dao.insert(
            Prompt(
                label = "/tran",
                type = TypeSelector.PROMPT_COMMAND.value,
                prompt = "Please translate the following content into Chinese: \\r\\n",
                desc = "Translation"
            )
        )
        dao.insert(
            Prompt(
                label = "Software",
                type = TypeSelector.PROMPT_ROLE.value,
                prompt = "I want you to act as a software developer. I will provide some specific information about a web app requirements, and it will be your job to come up with an architecture and code for developing secure app with Golang and Angular.",
                desc = "Software"
            )
        )
    }catch (_: Exception) {
        ;
    }
}

fun initPromData(context: Context) {
    runOnce{ initPromDataOnce(context) }
}
