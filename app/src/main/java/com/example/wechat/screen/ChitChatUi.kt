package com.example.wechat.screen

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.wechat.model.ChatMessage
import com.example.wechat.viewmodel.ChitChatViewModel


@Composable
fun ChitChatUi(chatId: String, senderId: String, receiverId: String, viewModel: ChitChatViewModel) {

    val messages by viewModel.messages.collectAsState()
    val messageText = remember {
        mutableStateOf("")
    }

    val selectedImageUri = remember {
        mutableStateOf<Uri?>(null)
    }

    LaunchedEffect(chatId) {
        viewModel.getMessages(chatId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = false
        ) {
            items(messages) { message ->
                ChitChatBubble(message, isMe = message.senderId == senderId)

            }
        }
    }
}

@Composable
fun ChitChatBubble(messages: ChatMessage, isMe: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = if (isMe) Alignment.End else Alignment.Start
    ) {
        if (messages.imageUrl.isNotEmpty()) {
            AsyncImage(
                model = messages.imageUrl, contentDescription = "image Message",
                modifier = Modifier.size(200.dp)
            )
        }
    }
}
