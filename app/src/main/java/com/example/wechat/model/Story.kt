package com.example.wechat.model

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID


data class Status(
    val name: String = "",
    val time: String = "",
    val imageUrl: String = ""
)


class StatusViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val storageRef = FirebaseStorage.getInstance().reference

    private val _statuses = MutableStateFlow<List<Status>>(emptyList())
    val statuses: StateFlow<List<Status>> = _statuses

    init {
        fetchStatuses()
    }

    // ✅ Fetch status list from Firebase
    private fun fetchStatuses() {
        db.collection("statuses").addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.e("Firebase", "Error fetching statuses", e)
                return@addSnapshotListener
            }
            val statusList = snapshots?.documents?.map { doc ->
                Status(
                    name = doc.getString("name") ?: "Unknown",
                    time = doc.getString("time") ?: "Unknown",
                    imageUrl = doc.getString("imageUrl") ?: ""
                )
            } ?: emptyList()
            _statuses.value = statusList
        }
    }

    // ✅ Upload image to Firebase Storage
    fun uploadStatus(name: String, imageUri: Uri, context: Context) {
        val imageRef = storageRef.child("status_images/${UUID.randomUUID()}.jpg")
        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                    saveStatusToFirestore(name, imageUrl.toString())
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Image Upload Failed", Toast.LENGTH_SHORT).show()
            }
    }

    // ✅ Save status to Firestore
    private fun saveStatusToFirestore(name: String, imageUrl: String) {
        val status = hashMapOf(
            "name" to name,
            "time" to "Just now",
            "imageUrl" to imageUrl
        )

        db.collection("statuses").add(status)
            .addOnSuccessListener { Log.d("Firebase", "Status Uploaded") }
            .addOnFailureListener { Log.e("Firebase", "Upload Failed", it) }
    }
}

@Composable
fun StatusScreen(
    navController: NavController,
    viewModel: StatusViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val name = "john"
    val time = "02:00"

    val statuses by viewModel.statuses.collectAsState()
    val context = LocalContext.current

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { selectedUri ->
            viewModel.uploadStatus("My Status", selectedUri, context)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(statuses) { status ->
                StatusItem(status, navController)
                Divider()
            }
        }

        FloatingActionButton(
            onClick = { pickImageLauncher.launch("image/*") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.Green
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Status", tint = Color.White)
        }
    }
}

@Composable
fun StatusItem(status: Status, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("statusView/${status.name}?imageUrl=${status.imageUrl}")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = status.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Green, CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = status.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = status.time, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
fun StatusApp() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "statusScreen") {
        composable("statusScreen") {
            StatusScreen(navController)
        }
        composable(
            "statusView/{statusName}?imageUrl={imageUrl}",
            arguments = listOf(
                navArgument("statusName") { type = NavType.StringType },
                navArgument("imageUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val statusName = backStackEntry.arguments?.getString("statusName") ?: "Unknown"
            val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
            StatusViewScreen(statusName, imageUrl, navController)
        }
    }
}

@Composable
fun StatusViewScreen(statusName: String, imageUrl: String, navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Hello imageURL")



        AsyncImage(
            model = imageUrl,
            contentDescription = "Status image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Placeholder or message if no image available
        Text(text = "No Image Available", color = Color.White)









        Text(
            text = statusName,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}
