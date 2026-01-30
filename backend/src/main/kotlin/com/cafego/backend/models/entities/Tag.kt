package com.cafego.backend.models.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "tags")
data class Tag(
    @Column(nullable = false, unique = true)
    val name: String
) : BaseEntity()