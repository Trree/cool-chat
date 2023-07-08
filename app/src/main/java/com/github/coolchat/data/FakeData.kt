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
    channelName = "#chat",
    channelMembers = 42
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
