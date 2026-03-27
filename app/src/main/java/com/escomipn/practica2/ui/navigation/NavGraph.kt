package com.escomipn.practica2.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.escomipn.practica2.ui.screens.LoginScreen
import com.escomipn.practica2.ui.screens.ProductFormScreen
import com.escomipn.practica2.ui.screens.ProductListScreen
import com.escomipn.practica2.ui.screens.RegisterScreen
import com.escomipn.practica2.data.model.Producto

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ProductList : Screen("productList")
    object ProductForm : Screen("productForm")
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    // --- ESTADO PARA EL PRODUCTO A EDITAR ---
    // Esta variable guardará el producto cuando le des clic al lápiz
    var productoAEditar by remember { mutableStateOf<Producto?>(null) }

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
                onAddProduct = {
                    productoAEditar = null // Si es producto nuevo, limpiamos el estado
                    navController.navigate(Screen.ProductForm.route)
                },
                onEditProduct = { producto ->
                    productoAEditar = producto // Guardamos el producto seleccionado
                    navController.navigate(Screen.ProductForm.route)
                }
            )
        }

        composable(Screen.ProductForm.route) {
            ProductFormScreen(
                productoParaEditar = productoAEditar, // <--- PASAMOS EL PRODUCTO REAL
                onSaveSuccess = {
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}