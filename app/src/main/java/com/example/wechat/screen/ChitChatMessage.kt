package com.example.wechat.screen

import android.net.Uri
import android.util.Log
import com.example.wechat.model.ChatMessage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID


class ChatRepository {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    // message fetching from real time
    fun getMessage(chatId: String, onMessagesReceived: (List<ChatMessage>) -> Unit
    ) { // chatMessage == chatmessage Data class
        db.collection("chats").document(chatId).collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                val messages =
                    snapshot?.documents?.mapNotNull { it.toObject(ChatMessage::class.java) }

                if (messages != null) {
                    onMessagesReceived(messages)
                }
            }
    }

    suspend fun sendMessage(
        chatId: String,
        senderId: String,
        receiverId: String,
        message: String,
        imageUrl: String = ""
    ) {
        val messageObj =
            ChatMessage(senderId, receiverId, message, imageUrl, System.currentTimeMillis())

        try {
            db.collection("chats").document(chatId).collection("messages")
                .add(messageObj).await()
        } catch (e: Exception) {

        }
    }

    // image upload to firebase storage

    suspend fun uploadImageToFS(imageUri: Uri): String? {
        return try {
            val imageRef = storage.reference.child("chat_images/${UUID.randomUUID()}.jpg")
            imageRef.putFile(imageUri).await()
            imageRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            Log.e("ChatRepository", "Image upload failed", e)
            null
        }
    }
}