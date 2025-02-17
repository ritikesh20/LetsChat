package com.example.wechat.routes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wechat.screen.CallHistory
import com.example.wechat.screen.ChatScreen
import com.example.wechat.screen.Communities
import com.example.wechat.screen.HomeScreen
import com.example.wechat.screen.ProfileScreen
import com.example.wechat.screen.SingInScreen
import com.example.wechat.screen.SingUpScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainApp() {

    Surface(modifier = Modifier.fillMaxSize()) {

        val navController = rememberNavController()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val start = if (currentUser != null) "homeScreen" else "login"

        NavHost(navController = navController, startDestination = start) {

            composable("login") {
                SingInScreen(navController)
            }

            composable("singup") {
                SingUpScreen(navController)
            }

            composable("homeScreen") {
                HomeScreen(navController)
            }

            composable("homepage") {
                HomeScreen(navController)
            }

            composable("chat/{channelId} & {channelName}", arguments = listOf(
                navArgument("channelId") {
                    type = NavType.StringType
                },
                navArgument("channelId") {
                    type = NavType.StringType
                }
            )) {
                val channelId = it.arguments?.getString("channelId") ?: ""
                val channelName = it.arguments?.getString("channelName") ?: ""
                ChatScreen(navController, channelId, channelName)
            }

            composable("logout") {
                navController.navigate("login")
            }

//            composable("statusScreen") {
//                StatusScreen(navController,viewModel)
//            }
//
//            composable("statusView/{statusName}?imageUrl={imageUrl}",
//                arguments = listOf(
//                    navArgument("statusName") { type = NavType.StringType },
//                    navArgument("imageUrl") { type = NavType.StringType }
//                )
//            ) { backStackEntry ->
//                val statusName = backStackEntry.arguments?.getString("statusName") ?: "Unknown"
//                val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
//
//                StatusViewScreen(statusName, imageUrl, navController)
//
//            }

            composable(Screen.CallHistory.screen) {
                CallHistory()
            }

            composable(Screen.Communities.screen) {
                Communities()
            }

            composable(Screen.Profile.screen) {
                ProfileScreen()
            }

        }
    }
}
