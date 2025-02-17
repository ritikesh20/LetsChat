package com.example.wechat.screen

import android.Manifest
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.wechat.R
import com.example.wechat.model.Message
import com.example.wechat.viewmodel.ChatViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ChatScreen(navController: NavController, channelId: String, channelName: String) {

    val viewModel: ChatViewModel = hiltViewModel()

    val chooserDialog = remember {
        mutableStateOf(false)
    }

    val cameraImageUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val cameraImageLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success ->
            if (success) {
                cameraImageUri.value?.let {
                    // SEND IMAGE TO SERVER.
                    viewModel.sendImageMessage(it, channelId)

                }
            }
        }

    val imageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.sendImageMessage(it, channelId)
            }
        }


    fun createImageUri(): Uri {

        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

        val storageDir = ContextCompat.getExternalFilesDirs(
            navController.context,
            Environment.DIRECTORY_PICTURES
        ).first()

        return FileProvider.getUriForFile(
            navController.context,
            "${navController.context.packageName}.provider",
            File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir).apply {
                cameraImageUri.value = Uri.fromFile(this)
            }
        )
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                cameraImageLauncher.launch(createImageUri())
            } else {

            }
        }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            val viewModel: ChatViewModel = hiltViewModel()

            LaunchedEffect(key1 = true) {
                viewModel.listenForMessages(channelId)
            }
            val messages = viewModel.message.collectAsState()

            ChatMessages(
                messages = messages.value,
                onSendMessage = { message ->
                    viewModel.sendMessage(channelId, message)
                }, onImageClicked = {
                    chooserDialog.value = true
                }, channelName = channelName
            )
        }


        if (chooserDialog.value) {
            ContentSelectionDialog(
                onCameraSelected = {
                    chooserDialog.value = false
                    if (navController.context.checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                        cameraImageLauncher.launch(createImageUri())
                    } else {
//                        navController.navigate("camera") // request permission
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }, onGallerySelected = {
                    chooserDialog.value = false
                    imageLauncher.launch("image/*")
                }
            )
        }
    }
}


@Composable
fun ContentSelectionDialog(onCameraSelected: () -> Unit, onGallerySelected: () -> Unit) {
    AlertDialog(onDismissRequest = { }, confirmButton = {
        TextButton(onClick = onCameraSelected) {
            Text(text = "Camera", color = Color.White)
        }
    },
        dismissButton = {
            TextButton(onClick = onGallerySelected) {
                Text(text = "Gallery")
            }
        },
        title = { Text(text = "Select your source?") },
        text = { Text(text = "Would you like to pick an image from the gallery or use the") }
    )
}


@Composable
fun ChatMessages(
    channelName: String,
    messages: List<Message>,
    onSendMessage: (String) -> Unit,
    onImageClicked: () -> Unit,
    ) {

    val hideKeyboardController = LocalSoftwareKeyboardController.current

    val msg = remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                ChannelItem(channelName = channelName) {
                }
            }
            items(messages) { messages ->
                ChatBubble(message = messages)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                msg.value = ""
                onImageClicked()
            }) {
                Image(
                    painter = painterResource(id = R.drawable.attach), contentDescription = "Open"
                )
            }
            TextField(
                value = msg.value,
                onValueChange = { msg.value = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text(text = "Typing a message") },
                keyboardActions = KeyboardActions(
                    onDone = {
//                        onSendMessage(msg.value)
//                        msg.value = ""
                        hideKeyboardController?.hide()
                    }
                )
            )
            IconButton(onClick = {
                onSendMessage(msg.value)
                msg.value = ""
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "send ")
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message) {

    val isCurrentUser = message.senderId == Firebase.auth.currentUser?.uid

    val bubbleColor = if (isCurrentUser) Color.Blue else Color.Green

    val alignment = if (!isCurrentUser) Alignment.CenterStart else Alignment.CenterEnd

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment // Correct usage
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Row ke andar chat bubble content
            if (!isCurrentUser) {
                Image(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp)) // Fixed spacing
            }

            Box(
                modifier = Modifier
                    .background(color = bubbleColor, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp) // Padding added
            ) {
                if (message.imageUrl != null) {
                    AsyncImage(
                        model = message.imageUrl,
                        contentDescription = null,
                        modifier = Modifier.size(200.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = message.message?.trim() ?: "",
                        color = Color.White
                    )
                }
            }
        }
    }
}

