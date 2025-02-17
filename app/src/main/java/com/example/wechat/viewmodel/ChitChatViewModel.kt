package com.example.wechat.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wechat.model.ChatMessage
import com.example.wechat.screen.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChitChatViewModel(private val repository: ChatRepository) : ViewModel() {

    private val _message = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _message

    // Messages fetching
    fun getMessages(chatId: String) {
        repository.getMessage(chatId) { fetchedMessage ->
            _message.value = fetchedMessage
        }
    }

    // Messages send krna

    fun sendMessage(chatId: String, senderId: String, receiverId: String, message: String) {
        viewModelScope.launch {
            repository.sendMessage(chatId, senderId, receiverId, message)
        }
    }

    // image Uploads krna aur message send krna

    fun sendImageMessage(chatId: String, senderId: String, receiverId: String, imagerUri: Uri) {
        viewModelScope.launch {
            val imageUrl = repository.uploadImageToFS(imagerUri)
            if (imageUrl != null) {
                repository.sendMessage(chatId, senderId, receiverId, "", imageUrl)
            }
        }
    }


}