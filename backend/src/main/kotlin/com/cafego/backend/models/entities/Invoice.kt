package com.cafego.backend.models.entities

import jakarta.persistence.*

@Entity
@Table(name = "invoices")
data class Invoice(

    @Column(nullable = false)
    val total: Double,

    @Column(nullable = false)
    var status: String = "PENDIENTE",

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @OneToMany(mappedBy = "invoice", cascade = [CascadeType.ALL], orphanRemoval = true)
    val details: MutableList<InvoiceDetail> = mutableListOf()

) : BaseEntity()