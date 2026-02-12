package com.cafego.app.models

// Caja para cada producto
data class InvoiceItemRequest(
    val productId: Long,
    val quantity: Int
)

// Caja principal del pedido
data class OrderRequest(
    val userId: Long,
    val items: List<InvoiceItemRequest>
)