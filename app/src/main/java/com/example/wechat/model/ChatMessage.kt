package com.example.wechat.model

data class ChatMessage(

    var senderId: String = "",
    val receiverId: String = "",
    val message: String = "",
    val imageUrl: String = "",
    val timestamp: Long = System.currentTimeMillis()

)
