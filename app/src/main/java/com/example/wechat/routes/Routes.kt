package com.example.wechat.routes


sealed class Screen(val screen: String) {

    object LogIn: Screen("logIn")
    object SingUp: Screen("singUp")
    object HomeScreen : Screen("homeScreen")
    object CallHistory : Screen("callHistory")
    object Status : Screen("status")
    object Profile : Screen("profile")
    object Communities : Screen("Communities")

}