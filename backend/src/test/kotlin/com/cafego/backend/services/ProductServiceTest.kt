package com.cafego.backend.services

import com.cafego.backend.exceptions.ProductAlreadyExistsException
import com.cafego.backend.exceptions.ProductNotFoundException
import com.cafego.backend.mappers.ProductMapper
import com.cafego.backend.models.entities.Product
import com.cafego.backend.models.requests.ProductRequest
import com.cafego.backend.repositories.ProductRepository
import com.cafego.backend.repositories.TagRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import java.util.Optional

class ProductServiceTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var tagRepository: TagRepository
    private lateinit var productMapper: ProductMapper
    private lateinit var productService: ProductService

    @BeforeEach
    fun setUp() {
        productRepository = mock(ProductRepository::class.java)
        tagRepository = mock(TagRepository::class.java)
        productMapper = ProductMapper() // Usamos el mapper real

        productService = ProductService(
            productRepository = productRepository,
            tagRepository = tagRepository,
            productMapper = productMapper
        )
    }

    @Test
    fun `SHOULD save a product GIVEN a valid request`() {
        // GIVEN
        val request = ProductRequest(
            name = "Café Americano",
            description = "Café negro",
            price = 1.50,
            stock = 10,
            tags = mutableListOf("Bebida")
        )

        val productEntity = productMapper.toEntity(request).apply { id = 1L }

        // Mocks
        `when`(productRepository.existsByName(request.name)).thenReturn(false)
        `when`(tagRepository.findByName(anyString())).thenReturn(Optional.empty())

        // 👇👇👇 CORRECCIÓN: Simulamos que la etiqueta se guarda correctamente 👇👇👇
        `when`(tagRepository.save(any())).thenAnswer { it.getArgument(0) }

        // Simulamos el guardado del producto
        `when`(productRepository.save(any(Product::class.java))).thenReturn(productEntity)

        // WHEN
        val result = productService.save(request)

        // THEN
        assertEquals(1L, result.id)
    }

    @Test
    fun `SHOULD throw ProductAlreadyExistsException GIVEN a duplicate name`() {
        val request = ProductRequest("Café Repetido", "Desc", 1.0, 5, mutableListOf())
        `when`(productRepository.existsByName(request.name)).thenReturn(true)

        assertThrows<ProductAlreadyExistsException> {
            productService.save(request)
        }
    }

    @Test
    fun `SHOULD return product GIVEN a valid ID`() {
        val product = Product("Test", "D", 1.0, 5).apply { id = 1L }
        `when`(productRepository.findById(1L)).thenReturn(Optional.of(product))

        val result = productService.findById(1L)
        assertNotNull(result)
        assertEquals(1L, result!!.id)
    }

    @Test
    fun `SHOULD throw ProductNotFoundException GIVEN an invalid ID`() {
        `when`(productRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows<ProductNotFoundException> { productService.findById(99L) }
    }

    @Test
    fun `SHOULD return all products`() {
        val p1 = Product("A", "D", 1.0, 1)
        val p2 = Product("B", "D", 1.0, 1)
        `when`(productRepository.findAll()).thenReturn(listOf(p1, p2))

        val result = productService.findAll()
        assertEquals(2, result.size)
    }

    @Test
    fun `SHOULD search products GIVEN a term`() {
        val p1 = Product("Café", "D", 1.0, 1)
        `when`(productRepository.search("Café")).thenReturn(listOf(p1))

        val result = productService.searchProducts("Café")
        assertEquals(1, result.size)
    }
}