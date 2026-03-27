package com.escomipn.practica2.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.escomipn.practica2.data.model.Producto
import com.escomipn.practica2.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormScreen(
    productoParaEditar: Producto? = null,
    onSaveSuccess: () -> Unit,
    onBack: () -> Unit
) {
    // Estados para los campos de texto
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // EFECTO: Sincroniza los campos cuando el producto para editar cambia o se carga
    LaunchedEffect(productoParaEditar) {
        nombre = productoParaEditar?.nombre ?: ""
        precio = productoParaEditar?.precio?.toString() ?: ""
        stock = productoParaEditar?.stock?.toString() ?: ""
    }

    val sharedPref = context.getSharedPreferences("AUTH_PREFS", Context.MODE_PRIVATE)
    val token = sharedPref.getString("jwt_token", "") ?: ""

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (productoParaEditar == null) "Nuevo Producto" else "Editar Producto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del Producto") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio (MXN)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Existencias") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val pNombre = nombre
                    val pPrecio = precio.toDoubleOrNull() ?: 0.0
                    val pStock = stock.toIntOrNull() ?: 0

                    if (pNombre.isNotEmpty() && pPrecio > 0) {
                        isLoading = true

                        // Si estamos editando, mantenemos el ID original
                        val productoAEnviar = Producto(
                            id = productoParaEditar?.id,
                            nombre = pNombre,
                            precio = pPrecio,
                            stock = pStock
                        )

                        val api = RetrofitClient.instance

                        // Lógica de decisión API
                        val call = if (productoParaEditar == null) {
                            api.crearProducto(token, productoAEnviar)
                        } else {
                            // IMPORTANTE: Asegúrate de que productoParaEditar.id no sea nulo aquí
                            api.actualizarProducto(token, productoParaEditar.id!!, productoAEnviar)
                        }

                        call.enqueue(object : Callback<Producto> {
                            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                                isLoading = false
                                if (response.isSuccessful) {
                                    Toast.makeText(context, "Guardado exitosamente", Toast.LENGTH_SHORT).show()
                                    onSaveSuccess()
                                } else {
                                    Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<Producto>, t: Throwable) {
                                isLoading = false
                                Toast.makeText(context, "Fallo de red: ${t.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    } else {
                        Toast.makeText(context, "Datos inválidos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text(if (productoParaEditar == null) "Guardar Producto" else "Actualizar Cambios")
                }
            }
        }
    }
}