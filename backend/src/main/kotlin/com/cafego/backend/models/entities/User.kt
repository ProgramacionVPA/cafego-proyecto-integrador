package com.cafego.backend.models.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(

    @Column(nullable = false, unique = true) // La cédula no se puede repetir
    val cedula: String,

    @Column(nullable = false)
    val fullName: String,

    @Column(nullable = false)
    val email: String,


) : BaseEntity()