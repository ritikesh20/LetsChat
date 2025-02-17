//package com.example.wechat.screen
//
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.Divider
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import coil3.compose.AsyncImage
//import com.example.wechat.R
//import com.example.wechat.viewmodel.Status
//import com.example.wechat.viewmodel.StatusViewModel
//
//
//@Composable
//fun StatusScreen(
//    navController: NavHostController,
//    viewModel: StatusViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
//) {
//
//    val statuses by viewModel.statuses.collectAsState()
//    val context = LocalContext.current
//
//    val pickImageLauncher =
//        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
//            uri?.let { selectedUri ->
//                viewModel.uploadStatus("My Status", selectedUri, context)
//            }
//        }
//
//    Box(modifier = Modifier
//        .fillMaxSize()
//        .padding(top = 30.dp)) {
//        LazyColumn(modifier = Modifier.fillMaxSize()) {
//            items(statuses) { status ->
//                StatusItem(status, navController)
//                Divider()
//            }
//        }
//        FloatingActionButton(
//            onClick = { pickImageLauncher.launch("image/*") },
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(32.dp),
//            containerColor = Color.Green
//        ) {
//            Icon(
//                imageVector = Icons.Default.Add,
//                contentDescription = "Add Status",
//                tint = Color.White
//            )
//        }
//    }
//}
//
//@Composable
//fun StatusItem(status: Status, navController: NavHostController) {
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable {
//                navController.navigate("statusView/${status.name}?imageUrl=${status.imageUrl}")
//            },
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        AsyncImage(
//            model = status.imageUrl,
//            contentDescription = null,
//            modifier = Modifier
//                .size(55.dp)
//                .border(2.dp, Color.Green, CircleShape),
//            contentScale = ContentScale.Crop
//        )
//        Spacer(modifier = Modifier.width(12.dp))
//        Column {
//            Text(text = status.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
//            Text(text = status.time, color = Color.Gray, fontSize = 12.sp)
//        }
//    }
//}
//
//
//@Composable
//fun StatusViewScreen(statusName: String, imageUrl: String, navController: NavController) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
////        AsyncImage(
////            model = imageUrl,
////            contentDescription = "$statusName's Status",
////            contentScale = ContentScale.Crop,
////            modifier = Modifier.fillMaxSize()
////        )
//        if (imageUrl.isNotEmpty()) {
//            AsyncImage(
//                model = imageUrl,
//                contentDescription = "$statusName's Status",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.size(200.dp)
//            )
//        } else {
//            // Placeholder if no image URL available
//            Text(text = "No Image Available", color = Color.White)
//        }
//
//        Text(
//            text = statusName,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.TopStart)
//                .padding(16.dp)
//        )
//
//        IconButton(
//            onClick = { navController.popBackStack() },
//            modifier = Modifier
//                .align(Alignment.TopStart)
//                .padding(16.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Default.ArrowBack,
//                contentDescription = "Back",
//                tint = Color.White
//            )
//        }
//    }
//}
//
//
//@Composable
//fun StatusHeader() {
//
//    var search by remember {
//        mutableStateOf("")
//    }
//
//    Column() {
//
//        Text(
//            text = "Updates", fontSize = 32.sp, fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(20.dp)
//        )
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 20.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Default.Search, contentDescription = "",
//                modifier = Modifier.size(50.dp)
//            )
//            OutlinedTextField(value = search, onValueChange = { search = it },
//                placeholder = { Text(text = "Search") })
//        }
//
//        Row(modifier = Modifier.fillMaxWidth()) {
//            Text(
//                text = "Status",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(start = 20.dp, top = 10.dp)
//            )
//            Spacer(modifier = Modifier.width(220.dp))
//
//            IconButton(onClick = {
//
//            }) {
//                Image(painter = painterResource(id = R.drawable.cemara), contentDescription = "")
//            }
//
//            IconButton(onClick = {
//
//            }) {
//                Icon(imageVector = Icons.Default.Edit, contentDescription = "")
//            }
//
//        }
//    }
//}
//
//
//@Composable
//fun StatsX() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(20.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .size(50.dp)
//                .clip(CircleShape)
//                .border(1.dp, Color.Blue, CircleShape)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.imagedp), contentDescription = "",
//                contentScale = ContentScale.Crop
//            )
//        }
//        Spacer(modifier = Modifier.width(10.dp))
//        Column(
//            modifier = Modifier.padding(5.dp)
//        ) {
//            Text(text = "Hritikesh Singh", fontSize = 14.sp, fontWeight = FontWeight.Bold)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "24h ago", fontSize = 10.sp, color = Color.Gray)
//        }
//
//    }
//    HorizontalDivider()
//}