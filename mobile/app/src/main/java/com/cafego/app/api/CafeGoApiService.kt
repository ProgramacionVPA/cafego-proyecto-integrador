package com.cafego.app.api

import com.cafego.app.models.OrderRequest // <--- OJO CON ESTE IMPORT
import com.cafego.app.models.Product
import com.cafego.app.models.User
import com.cafego.app.models.UserRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CafeGoApiService {

    @POST("/api/users/identify")
    fun identifyUser(@Body request: UserRequest): Call<User>

    @GET("/api/products")
    fun getProducts(): Call<List<Product>>

    // --- NUEVO: ENVIAR PEDIDO ---
    @POST("/api/invoices")
    fun createOrder(@Body request: OrderRequest): Call<Void>
}