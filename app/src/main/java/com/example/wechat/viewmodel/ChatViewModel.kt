package com.example.wechat.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.wechat.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {

    private val _message = MutableStateFlow<List<Message>>(emptyList())
    val message = _message.asStateFlow()
    val db = Firebase.database

    fun sendMessage(channelID: String, messageText: String?, image: String? = null) {
        val message = Message(
            db.reference.push().key ?: UUID.randomUUID().toString(),
            Firebase.auth.currentUser?.uid ?: "",
            messageText,
            System.currentTimeMillis(),
            Firebase.auth.currentUser?.displayName ?: "",
            null,
            image
        )
//        db.getReference("message").child(channelID).push().setValue(message)
        db.reference.child("messages").child(channelID).push().setValue(message)
    }

    fun sendImageMessage(uri: Uri, channelId: String) {
        val imageRef = Firebase.storage.reference.child("images/${UUID.randomUUID()}")
        imageRef.putFile(uri)
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }
            .addOnCompleteListener() { task ->

                val currentUser = Firebase.auth.currentUser

                if (task.isSuccessful) {
                    val downloadUri = Firebase.auth.currentUser

                    sendMessage(channelId, null, downloadUri.toString())
                }


//                if (task.isSuccessful) {
//                    val downloadUri = task.result
//                    val message = Message(
//                        db.reference.push().key ?: UUID.randomUUID().toString(),
//                        currentUser?.uid ?: "",
//                        null,
//                        System.currentTimeMillis(),
//                        currentUser?.displayName ?: "",
//                        null,
//                        downloadUri.toString()
//                    )
//                    db.reference.child("message").child(channelId).push().setValue(message)
//                }


            }
    }


    fun listenForMessages(channelID: String) {
        db.getReference("messages").child(channelID).orderByChild("createdAt")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<Message>()
                    snapshot.children.forEach { data ->
                        val message = data.getValue(Message::class.java)
                        message?.let {
                            list.add(it)
                        }
                    }
                    _message.value = list
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
}
