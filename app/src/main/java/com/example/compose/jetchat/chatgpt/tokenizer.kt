package com.example.compose.jetchat.chatgpt

import android.util.Log


fun getTokenNum(content : String) : Int {
    Log.i("jet-chat", content)
    // java.lang.IllegalArgumentException: Unsupported flags: 256
//    val registry = Encodings.newLazyEncodingRegistry()
//    val enc: Encoding = registry.getEncoding(EncodingType.CL100K_BASE)
//    return  enc.countTokens(content)
    return (content.length * 1.2).toInt()
}