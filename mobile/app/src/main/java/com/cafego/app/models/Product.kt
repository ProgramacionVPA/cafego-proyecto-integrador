package com.cafego.app.models

data class Product(
    val id: Long,
    val name: String,
    val description: String?,
    val price: Double,
    val stock: Int,
    val isAvailable: Boolean,
    val tags: List<String>? = null // 👈 ESTA ES LA LÍNEA QUE FALTA
)