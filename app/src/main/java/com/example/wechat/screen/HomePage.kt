package com.example.wechat.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wechat.R
import com.example.wechat.routes.Screen


@Composable
fun HeaderChat() {

    var searchText = remember {
        TextFieldValue("")
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 20.dp, end = 20.dp)
        ) {
            Icon(imageVector = Icons.Default.Menu, contentDescription = "")
            Spacer(modifier = Modifier.width(270.dp))
            IconButton(onClick = {

            }) {
                Image(painter = painterResource(id = R.drawable.cemara), contentDescription = "")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Icon(imageVector = Icons.Default.AddCircle, contentDescription = "")
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Chats", fontSize = 36.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Searchbar")
            }
            TextField(
                modifier = Modifier.padding(end = 10.dp, bottom = 5.dp),
                shape = RoundedCornerShape(10.dp),
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = {
                    Text(
                        text = "Ask Meta Ai or Search",
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray.copy(0.9f)
                    )
                },
                singleLine = true
            )
        }

    }
}

@Composable
fun BottomNavBar(navController: NavController) {


    Card() {
        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {

            IconButton(onClick = { navController.navigate("statusScreen") }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Updates")
            }

            IconButton(
                onClick = { navController.navigate(Screen.CallHistory.screen) },
            ) {
                Icon(imageVector = Icons.Default.Call, contentDescription = "Updates")

            }

            IconButton(onClick = { navController.navigate(Screen.Communities.screen) }) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Updates")
            }

            IconButton(onClick = { navController.navigate("homeScreen") }) {
                Image(painter = painterResource(id = R.drawable.chaticons), contentDescription = "")
            }


            IconButton(onClick = { navController.navigate(Screen.Profile.screen) }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Updates")
            }


        }
    }


}




