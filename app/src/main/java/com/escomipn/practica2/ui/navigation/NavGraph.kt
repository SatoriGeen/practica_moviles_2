package com.escomipn.practica2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.escomipn.practica2.ui.screens.LoginScreen
import com.escomipn.practica2.ui.screens.ProductFormScreen
import com.escomipn.practica2.ui.screens.ProductListScreen
import com.escomipn.practica2.ui.screens.RegisterScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ProductList : Screen("productList")
    object ProductForm : Screen("productForm")
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.ProductList.route) },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { navController.popBackStack() },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(Screen.ProductList.route) {
            ProductListScreen(
                onAddProduct = { navController.navigate(Screen.ProductForm.route) },
                onEditProduct = { product ->
                    navController.navigate(Screen.ProductForm.route)
                }
            )
        }
        composable(Screen.ProductForm.route) {
            ProductFormScreen(
                onSave = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
