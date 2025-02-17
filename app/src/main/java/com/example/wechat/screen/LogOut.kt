package com.example.wechat.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


fun LogOut() {

    val auth = FirebaseAuth.getInstance()
    val _firebaseUser = MutableLiveData<FirebaseUser?>()

    auth.signOut()
    _firebaseUser.postValue(null)

}


@Composable
fun LogOutScreen() {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = { LogOut() }) {
            Text(text = "LogOut")
        }
    }

}