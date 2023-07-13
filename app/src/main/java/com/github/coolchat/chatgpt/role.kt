package com.github.coolchat.chatgpt

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.coolchat.data.PromptDatabase
import com.github.coolchat.data.TypeSelector

private const val ROLE_COLUMNS = 5

@Composable
fun RoleTable(
    onTextAdded: (String) -> Unit,
    onMessageSent: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth()) {
        val context = LocalContext.current
        val database by lazy { PromptDatabase.getDatabase(context) }
        Log.i("cool-chat", "start")
        val roles = database.promptDao().getAllType(TypeSelector.PROMPT_ROLE.value)
        val roleList = roles.map { Pair(it.label, it.prompt) }.toList()
        Log.i("cool-chat", roleList.toString())
        repeat(4) { x ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(ROLE_COLUMNS) { y ->
                    val index = x * ROLE_COLUMNS + y
                    if (roleList.size <= index) {
                        return
                    }
                    val role = roleList[index]
                    if (role.first.isNullOrEmpty() || role.second.isNullOrEmpty()) {
                        return
                    }
                    Text(
                        modifier = Modifier
                            .clickable(onClick = {
                                onTextAdded(role.second)
                                onMessageSent()
                            })
                            .sizeIn(minWidth = 42.dp, minHeight = 42.dp)
                            .padding(8.dp),
                        text = role.first,
                        style = LocalTextStyle.current.copy(
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}

