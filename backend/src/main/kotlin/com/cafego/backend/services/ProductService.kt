package com.cafego.backend.services

import com.cafego.backend.mappers.ProductMapper
import com.cafego.backend.models.entities.Tag
import com.cafego.backend.models.requests.ProductRequest
import com.cafego.backend.models.responses.ProductResponse
import com.cafego.backend.repositories.ProductRepository
import com.cafego.backend.repositories.TagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val tagRepository: TagRepository, // <--- Inyectamos esto nuevo
    private val productMapper: ProductMapper
) {

    @Transactional // Importante: Si algo falla, no guarda nada (ni producto ni tags)
    fun save(request: ProductRequest): ProductResponse {
        // 1. Convertimos el Request básico a Entidad
        val productEntity = productMapper.toEntity(request)

        // 2. Procesamos las etiquetas (Tags)
        request.tags.forEach { tagName ->
            // Buscamos si la etiqueta ya existe en la BD
            val tag = tagRepository.findByName(tagName)
                .orElseGet {
                    // Si NO existe, la creamos y guardamos al instante
                    tagRepository.save(Tag(name = tagName))
                }
            // Agregamos la etiqueta (nueva o existente) al producto
            productEntity.tags.add(tag)
        }

        // 3. Guardamos el producto con sus relaciones
        val savedProduct = productRepository.save(productEntity)

        // 4. Devolvemos la respuesta
        return productMapper.toResponse(savedProduct)
    }

    // Listar todos
    fun findAll(): List<ProductResponse> {
        return productRepository.findAll()
            .map { productMapper.toResponse(it) }
    }

    // Buscar por ID
    fun findById(id: Long): ProductResponse? {
        val foundProduct = productRepository.findById(id)
            .orElseThrow {
                com.cafego.backend.exceptions.ProductNotFoundException("El producto con id $id no existe")
            }
        return productMapper.toResponse(foundProduct)
    }

    // Búsqueda inteligente (Search)
    fun searchProducts(term: String): List<ProductResponse> {
        val products = productRepository.search(term)
        return products.map { productMapper.toResponse(it) }
    }
}