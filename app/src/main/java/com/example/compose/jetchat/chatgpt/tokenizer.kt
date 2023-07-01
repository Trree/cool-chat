package com.example.compose.jetchat.chatgpt

import android.util.Log
import com.knuddels.jtokkit.Encodings
import com.knuddels.jtokkit.api.Encoding
import com.knuddels.jtokkit.api.EncodingType


fun getTokenNum(content : String) : Int {
    Log.i("jet-chat", content)
//    val registry = Encodings.newLazyEncodingRegistry()
//    val enc: Encoding = registry.getEncoding(EncodingType.CL100K_BASE)
//    return  enc.countTokens(content)
    //todo base:https://github.com/knuddelsgmbh/jtokkit
    //todo backend Because it consumes a large amount of memory, all simple calculations.
    return (content.length * 1.2).toInt()
}