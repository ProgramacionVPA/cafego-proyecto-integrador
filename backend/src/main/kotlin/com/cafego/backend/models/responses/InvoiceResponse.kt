package com.cafego.backend.models.responses

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class InvoiceResponse(
    val id: Long,
    val total: Double,

    @JsonProperty("client_name")
    val clientName: String,

    val status: String,

    val items: List<InvoiceItemResponse>,

    @JsonProperty("created_at")
    val createdAt: LocalDateTime?
)

data class InvoiceItemResponse(
    val productName: String,
    val quantity: Int,
    val unitPrice: Double,
    val subtotal: Double
)