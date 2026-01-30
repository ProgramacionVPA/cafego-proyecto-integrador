package com.cafego.backend.models.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String, // Aquí guardaremos la contraseña

    @Column(name = "full_name")
    val fullName: String
) : BaseEntity()