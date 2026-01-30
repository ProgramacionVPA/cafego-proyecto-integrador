package com.cafego.backend.models.responses

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class InvoiceResponse(
    val id: Long,
    val total: Double,

    @JsonProperty("client_name")
    val clientName: String, // Devolvemos el nombre, no el objeto usuario entero

    val items: List<InvoiceItemResponse>,

    @JsonProperty("created_at")
    val createdAt: LocalDateTime?
)

// Detalle de cada ítem en la respuesta
data class InvoiceItemResponse(
    val productName: String,
    val quantity: Int,
    val unitPrice: Double,
    val subtotal: Double
)