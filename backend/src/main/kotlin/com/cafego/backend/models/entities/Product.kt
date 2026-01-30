package com.cafego.backend.models.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "products")
data class Product(

    @Column(nullable = false)
    val name: String,

    @Column(columnDefinition = "TEXT")
    val description: String,

    @Column(nullable = false)
    val price: Double,

    @Column(nullable = false)
    val stock: Int,

    // Mapeo exacto como le gusta al profe
    @Column(name = "is_available")
    val isAvailable: Boolean = true

) : BaseEntity() // <--- ¡AQUÍ ESTÁ LA MAGIA! Hereda ID y Fechas