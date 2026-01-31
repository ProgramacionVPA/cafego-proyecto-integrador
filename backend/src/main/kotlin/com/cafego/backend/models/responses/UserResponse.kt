package com.cafego.backend.models.responses

data class UserResponse(
    val id: Long,
    val fullName: String,
    val email: String,
    val cedula: String
)