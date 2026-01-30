package com.cafego.backend.services

import com.cafego.backend.mappers.ProductMapper
import com.cafego.backend.models.requests.ProductRequest
import com.cafego.backend.models.responses.ProductResponse
import com.cafego.backend.repositories.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper
) {

    // Guardar
    fun save(request: ProductRequest): ProductResponse {
        // Aquí podrías validar stock máximo si quisieras, como en tu ejemplo

        val entity = productMapper.toEntity(request)
        val savedProduct = productRepository.save(entity)
        return productMapper.toResponse(savedProduct)
    }

    // Listar todos
    fun findAll(): List<ProductResponse> {
        return productRepository.findAll()
            .map { productMapper.toResponse(it) }
    }

    // En ProductService.kt -> findById
    fun findById(id: Long): ProductResponse? {
        val foundProduct = productRepository.findById(id)
            .orElseThrow {
                // Usamos la excepción EXACTA del paquete exceptions
                com.cafego.backend.exceptions.ProductNotFoundException("Product with id $id not found")
            }
        return productMapper.toResponse(foundProduct)
    }
}