package com.cafego.backend.mappers

import com.cafego.backend.models.entities.Invoice
import com.cafego.backend.models.responses.InvoiceItemResponse
import com.cafego.backend.models.responses.InvoiceResponse
import org.springframework.stereotype.Component

@Component
class InvoiceMapper {

    fun toResponse(entity: Invoice): InvoiceResponse {
        return InvoiceResponse(
            id = entity.id ?: 0,
            total = entity.total,
            clientName = entity.user.fullName, // Extraemos solo el nombre del cliente
            createdAt = entity.createdAt,
            items = entity.details.map { detail ->
                InvoiceItemResponse(
                    productName = detail.product.name,
                    quantity = detail.quantity,
                    unitPrice = detail.unitPrice,
                    subtotal = detail.quantity * detail.unitPrice
                )
            }
        )
    }
}