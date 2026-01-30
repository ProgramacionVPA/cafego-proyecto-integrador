package com.cafego.backend.models.requests

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProductRequest(
    @field:NotBlank(message = "El nombre es obligatorio")
    val name: String,

    val description: String,

    @field:NotNull(message = "El precio es obligatorio")
    @field:Min(value = 0, message = "El precio no puede ser negativo")
    val price: Double,

    @field:Min(value = 0, message = "El stock no puede ser negativo")
    val stock: Int
)