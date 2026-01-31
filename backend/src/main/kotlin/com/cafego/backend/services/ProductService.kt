package com.cafego.backend.services

// CORRECCIÓN AQUÍ: Importamos desde 'exceptions', no desde 'handlers'
import com.cafego.backend.exceptions.ProductAlreadyExistsException
import com.cafego.backend.exceptions.ProductNotFoundException
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
    private val tagRepository: TagRepository,
    private val productMapper: ProductMapper
) {

    @Transactional
    fun save(request: ProductRequest): ProductResponse {
        // --- EL GUARDIA (VALIDACIÓN DE DUPLICADOS) ---
        if (productRepository.existsByName(request.name)) {
            throw ProductAlreadyExistsException("El producto '${request.name}' ya existe")
        }

        // 1. Convertimos Request a Entidad
        val productEntity = productMapper.toEntity(request)

        // 2. Procesamos Etiquetas
        request.tags.forEach { tagName ->
            val tag = tagRepository.findByName(tagName)
                .orElseGet {
                    tagRepository.save(Tag(name = tagName))
                }
            productEntity.tags.add(tag)
        }

        // 3. Guardamos
        val savedProduct = productRepository.save(productEntity)

        // 4. Retornamos
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
                ProductNotFoundException("El producto con id $id no existe")
            }
        return productMapper.toResponse(foundProduct)
    }

    // Búsqueda inteligente
    fun searchProducts(term: String): List<ProductResponse> {
        val products = productRepository.search(term)
        return products.map { productMapper.toResponse(it) }
    }
}