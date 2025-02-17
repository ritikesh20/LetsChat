package com.example.wechat.model

data class Channel(
    val id: String = "",
    val name: String = "",
    val createAt: Long = System.currentTimeMillis()
)

