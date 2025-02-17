package com.example.wechat.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wechat.R
import com.example.wechat.routes.Screen
import com.example.wechat.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    Scaffold(
        topBar = { HeaderChat() },
        bottomBar = { BottomNavBar(navController) },
        modifier = Modifier.padding(bottom = 50.dp)
    ) {

        Column(modifier = Modifier.padding(it)) {

            val viewModel = hiltViewModel<HomeViewModel>()
            val channels = viewModel.channels.collectAsState()

            val addChannel = remember {
                mutableStateOf(false)
            }

            val sheetState = rememberModalBottomSheetState()

            Scaffold(
                floatingActionButton = { // Add new chat
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Blue)
                            .clickable {
                                addChannel.value = true
                            }
                    ) {
                        Text(
                            text = "New Chat",
                            modifier = Modifier.padding(16.dp),
                            color = Color.White
                        )
                    }
                }
            ) {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(channels.value) { channel ->

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .clickable {
                                        navController.navigate("chat/${channel.id} & ${channel.name}")
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.person),
                                        contentDescription = "Profile Image",
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Column {
                                        Text(
                                            text = channel.name,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Last message...",
                                            fontSize = 14.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }
                                Text(
                                    text = "Yesterday",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }

            }
            if (addChannel.value) {
                ModalBottomSheet(
                    onDismissRequest = { addChannel.value = false },
                    sheetState = sheetState
                ) {
                    AddChannelDialog {
                        viewModel.addXchannel(it)
                        addChannel.value = false
                    }
                }
            }
        }
    }
}


// chat screen ui ux
@Composable
fun ChannelItem(channelName: String, onClick: () -> Unit) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(0.5f))
            .clickable {

            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(70.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.5f))
        ) {
            Image(
                painter = painterResource(id = R.drawable.person), contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
        }
        Text(
            text = channelName, modifier = Modifier.padding(8.dp),
            fontSize = 18.sp, fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.width(150.dp))

        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Call, contentDescription = "Calling....")
        }

        IconButton(onClick = { /*TODO*/ }) {
            Image(
                painter = painterResource(id = R.drawable.cemara),
                contentDescription = "videoCalling..."
            )
        }


    }
}


// add channel design
@Composable
fun AddChannelDialog(onAddChannel: (String) -> Unit) {

    val channelName = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("New Chat")
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = channelName.value, onValueChange = {
                channelName.value = it
            },
            label = { Text(text = "Channel Name") }, singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onAddChannel(channelName.value) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add")
        }

    }
}


