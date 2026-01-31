package com.cafego.backend.models.entities

import jakarta.persistence.*
import java.time.LocalDateTime

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
    var stock: Int,

    @Column(name = "is_available")
    val isAvailable: Boolean = true,

    //Relación Muchos a Muchos
    @ManyToMany(fetch = FetchType.EAGER) // EAGER para que traiga los tags al cargar el producto
    @JoinTable(
        name = "product_tags",
        joinColumns = [JoinColumn(name = "product_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    val tags: MutableSet<Tag> = mutableSetOf()

) : BaseEntity()