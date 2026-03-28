package com.escomipn.practica2.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.escomipn.practica2.data.dto.LoginResponse
import com.escomipn.practica2.data.model.Usuario
import com.escomipn.practica2.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bienvenido",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Inicia sesión para continuar",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- BOTÓN CON LÓGICA DE LOGIN ---
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    isLoading = true
                    val user = Usuario(email, password)


                    RetrofitClient.instance.login(user).enqueue(object : Callback<String> { // <-- Cambia LoginResponse por String
                        override fun onResponse(call: Call<String>, response: Response<String>) { // <-- Cambia aquí también
                            isLoading = false
                            if (response.isSuccessful && response.body() != null) {
                                val respuestaServidor = response.body()!!

                                // LOG TEMPORAL: Esto imprimirá en tu Logcat qué está mandando Julio
                                android.util.Log.d("DEBUG_LOGIN", "El servidor respondió: $respuestaServidor")

                                // Si el servidor manda el token puro como texto:
                                val sharedPref = context.getSharedPreferences("AUTH_PREFS", Context.MODE_PRIVATE)
                                with(sharedPref.edit()) {
                                    putString("jwt_token", "Bearer $respuestaServidor")
                                    apply()
                                }

                                Toast.makeText(context, "Sesión iniciada", Toast.LENGTH_SHORT).show()
                                onLoginSuccess()
                            } else {
                                // Imprime el código de error para saber si es 401, 403 o 500
                                android.util.Log.e("DEBUG_LOGIN", "Error código: ${response.code()}")
                                Toast.makeText(context, "Credenciales inválidas o error de servidor", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) { // <-- Cambia aquí también
                            isLoading = false
                            Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(context, "Llenar todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Iniciar Sesión")
            }
        }

        TextButton(onClick = onNavigateToRegister, enabled = !isLoading) {
            Text("¿No tienes cuenta? Regístrate aquí")
        }
    }
}