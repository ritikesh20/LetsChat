package com.example.wechat.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.wechat.R
import com.example.wechat.viewmodel.SignUpState
import com.example.wechat.viewmodel.SignUpViewModel

@Composable
fun SingUpScreen(navController: NavHostController) {

    val viewModel: SignUpViewModel = hiltViewModel()
    val uiState by viewModel.state.collectAsState()

    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    LaunchedEffect(uiState) {
        when (uiState) {
            is SignUpState.Success -> {
                navController.navigate("homeScreen")
            }

            is SignUpState.Error -> {
                Toast.makeText(context, "Sign Up failed", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
//            .verticalScroll(
//                rememberScrollState()
//            )
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logothreads), contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
            )

            Text(
                text = "SingUp Page",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(value = name, onValueChange = { name = it },
                label = { Text(text = "Name") }
            )


            OutlinedTextField(value = email, onValueChange = { email = it },
                label = { Text(text = "Email") }
            )

            OutlinedTextField(value = password, onValueChange = { password = it },
                label = { Text(text = "password") }
            )

            Button(onClick = {
                viewModel.singUp(name, email, password)
            }) {
                Text(text = "SingUp")
            }
            Row(
//                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Already a User ? Go To ")

                Text(text = "Login ->",
                    modifier = Modifier.clickable {
                        navController.navigate("login")
                    }
                )
            }
        }
    }
}
