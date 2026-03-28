package com.escomipn.practica2.data.network

import com.escomipn.practica2.data.dto.LoginResponse
import com.escomipn.practica2.data.model.Producto
import com.escomipn.practica2.data.model.Usuario
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("auth/registro")
    fun registrar(@Body usuario: Usuario): Call<Void>

    @POST("auth/login")
    fun login(@Body usuario: Usuario): Call<String> // O Call<ResponseBody>

    // --- CRUD DE PRODUCTOS ---
    @GET("productos")
    fun obtenerProductos(@Header("Authorization") token: String): Call<List<Producto>>

    @POST("productos")
    fun crearProducto(@Header("Authorization") token: String, @Body producto: Producto): Call<Producto>

    @PUT("productos/{id}")
    fun actualizarProducto(@Header("Authorization") token: String, @Path("id") id: Int, @Body producto: Producto): Call<Producto>

    @DELETE("productos/{id}")
    fun eliminarProducto(@Header("Authorization") token: String, @Path("id") id: Int): Call<Void>
}