package com.github.coolchat.conversation

fun checkAndGetSlashString(str: String): String {
    if(str.isNotEmpty() && str[0] == '/') {
        val index = str.indexOf(" ")
        return str.substring(0, index)
    }
    return ""
}