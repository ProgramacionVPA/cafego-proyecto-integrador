package com.cafego.backend.controllers

import com.cafego.backend.models.requests.ProductRequest
import com.cafego.backend.models.responses.ProductResponse
import com.cafego.backend.services.ProductService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping
    fun save(@Valid @RequestBody request: ProductRequest): ProductResponse {
        return productService.save(request)
    }

    @GetMapping
    fun findAll(): List<ProductResponse> {
        return productService.findAll()
    }

    // Usamos @PathVariable porque es más estándar que @RequestParam
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ProductResponse? {
        return productService.findById(id)
    }
}