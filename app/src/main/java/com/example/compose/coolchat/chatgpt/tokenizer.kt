package com.example.compose.coolchat.chatgpt

import android.util.Log
import com.knuddels.jtokkit.Encodings
import com.knuddels.jtokkit.api.Encoding
import com.knuddels.jtokkit.api.EncodingType


fun getTokenNum(content : String) : Int {
    Log.i("jet-chat", content)
    val roughlyCal = true
    return if (roughlyCal) {
        (content.length * 1.2).toInt()
    } else {
        val registry = Encodings.newLazyEncodingRegistry()
        val enc: Encoding = registry.getEncoding(EncodingType.CL100K_BASE)
        enc.countTokens(content)
    }
}