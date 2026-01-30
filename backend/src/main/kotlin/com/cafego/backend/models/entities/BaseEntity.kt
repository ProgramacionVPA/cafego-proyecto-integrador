package com.cafego.backend.models.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@MappedSuperclass // Indica que esto es un molde para otras tablas
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
}