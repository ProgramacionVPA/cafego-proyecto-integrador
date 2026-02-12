package com.cafego.app.models

// Lo que enviamos para identificarnos
data class UserRequest(
    val cedula: String,
    val fullName: String,
    val email: String
)

// Lo que recibimos del servidor
data class User(
    val id: Long,
    val cedula: String,
    val fullName: String,
    val email: String
)