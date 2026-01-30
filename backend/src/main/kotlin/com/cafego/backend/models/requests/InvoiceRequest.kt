package com.cafego.backend.models.requests

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class InvoiceRequest(
    @field:NotNull(message = "El ID del usuario es obligatorio")
    val userId: Long,

    @field:NotEmpty(message = "La factura debe tener al menos un producto")
    val items: List<InvoiceItemRequest>
)

// Clase auxiliar para saber qué productos lleva (ID y Cantidad)
data class InvoiceItemRequest(
    @field:NotNull val productId: Long,
    @field:NotNull val quantity: Int
)