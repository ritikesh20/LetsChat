package com.example.wechat.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wechat.screen.SignInState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {

    //private val auth = FirebaseAuth.getInstance()
    private val _state = MutableStateFlow<SignInState>(SignInState.Nothing)
    val state = _state.asStateFlow()

    fun signIn(email: String, password: String) {
        _state.value = SignInState.Loading

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _state.value = SignInState.Success
            }
            .addOnFailureListener { exception ->
                _state.value = SignInState.Error
            }

    }
}

