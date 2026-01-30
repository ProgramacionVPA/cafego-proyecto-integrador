package com.cafego.backend.mappers

import com.cafego.backend.models.entities.Product
import com.cafego.backend.models.requests.ProductRequest
import com.cafego.backend.models.responses.ProductResponse
import org.springframework.stereotype.Component

@Component
class ProductMapper {

    fun toEntity(request: ProductRequest): Product {
        return Product(
            name = request.name,
            description = request.description,
            price = request.price,
            stock = request.stock,
            isAvailable = true // Por defecto disponible
        )
    }

    fun toResponse(entity: Product): ProductResponse {
        return ProductResponse(
            id = entity.id ?: 0,
            name = entity.name,
            description = entity.description,
            price = entity.price,
            stock = entity.stock,
            isAvailable = entity.isAvailable,

            // --- AQUÍ ESTÁ EL TRUCO ---
            // Tomamos la lista de objetos Tag y extraemos solo sus nombres
            tags = entity.tags.map { it.name }.toSet(),

            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}