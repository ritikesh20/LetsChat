package com.example.wechat

import android.app.Application
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.wechat.model.StatusApp
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import java.util.UUID

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            MainApp()
            StatusApp()
            var navController = rememberNavController()
//            HomePage(navController)
        }
    }
}


@HiltAndroidApp
class Chatter : Application() {


}

@Composable
fun ImagePicker(onImageSelected: (Uri) -> Unit) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                onImageSelected(it)
            }
        }

    IconButton(onClick = { launcher.launch("image/*") }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "")
    }
}

//fun UploadImageToFb(imageUri: Uri, chatId: String, sendId: String, receiverId: String) {
//
//    val storageRef = FirebaseStorage.getInstance().reference
//    val imageRef = storageRef.child("chat_Images/${UUID.randomUUID()}.jpg")
//
//    imageRef.putFile(imageUri)
//        .addOnSuccessListener {
//            imageRef.downloadUrl.addOnSuccessListener { uri ->
//                val imageUrl = uri.toString()
//
//            }
//        }
//        .addOnFailureListener {
//
//        }
//}





