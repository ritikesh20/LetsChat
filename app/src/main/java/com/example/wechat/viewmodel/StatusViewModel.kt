//package com.example.wechat.viewmodel
//
//import android.content.Context
//import android.net.Uri
//import android.util.Log
//import android.widget.Toast
//import androidx.lifecycle.ViewModel
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.storage.FirebaseStorage
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import java.util.UUID
//import com.example.wechat.viewmodel.Status
//
//data class Status(
//    val name: String = "",
//    val time: String = "",
//    val imageUrl: String = ""
//)
//
////Ye ViewModel Firebase Firestore se status list fetch karega aur
//// Firebase Storage me status upload karega.
//
//class StatusViewModel : ViewModel() {
//
//    private val db = FirebaseFirestore.getInstance()
//    private val storageRef = FirebaseStorage.getInstance().reference
//
//    private val _statuses = MutableStateFlow<List<Status>>(emptyList())
//    val statuses: StateFlow<List<Status>> = _statuses
//
//
//    init {
//        fetchStatues()
//    }
//
//    // fetch status list from firebase
//    private fun fetchStatues() {
//        db.collection("statuses").addSnapshotListener { snapshots, e ->
//            if (e != null) {
//                Log.e("Firebase", "Error", e)
//                return@addSnapshotListener
//            }
//
//            val statusList = snapshots?.documents?.map { doc ->
//                Status(
//                    name = doc.getString("name") ?: "Unknown",
//                    time = doc.getString("time") ?: "Unknown",
//                    imageUrl = doc.getString("imageUrl") ?: ""
//                )
//            } ?: emptyList()
//            _statuses.value = statusList
//        }
//    }
//
//    // ✅ Upload image to Firebase Storage
//    fun uploadStatus(name: String, imageUri: Uri, context: Context) {
//        val imageRef =
//            storageRef.child("status_images/${UUID.randomUUID()}.jpg") // val storage = inFirebasefolder
//        imageRef.putFile(imageUri) // upload image to firebase
//            .addOnSuccessListener {
//                imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
//                    saveStatusToFirestore(name, imageUrl.toString())
//                }
//            }.addOnFailureListener {
//                Toast.makeText(context, "Image Upload Failed", Toast.LENGTH_LONG).show()
//            }
//    }
//
//    private fun saveStatusToFirestore(name: String, imageUrl: String) {
//        val status = hashMapOf(
//            "name" to name,
//            "time" to "jest now",
//            "imageUrl" to imageUrl
//        )
//
//        db.collection("statuses").add(status)
//            .addOnSuccessListener { Log.d("Firebase", "Status Uploaded") }
//            .addOnFailureListener { Log.d("Firebase", "Upload Failed", it) }
//    }
//}
//
//// Ab hum StatusScreen me ViewModel ka use karenge.
//
