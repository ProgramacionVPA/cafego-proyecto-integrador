package com.cafego.backend.models.responses

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ProductResponse(
    val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,

    @JsonProperty("is_available")
    val isAvailable: Boolean,

    // Devolveremos solo los nombres: ["Caliente", "Desayuno"]
    val tags: Set<String> = emptySet(),

    @JsonProperty("created_at")
    val createdAt: LocalDateTime?,

    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime?
)