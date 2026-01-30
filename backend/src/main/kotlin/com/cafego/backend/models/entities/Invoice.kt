package com.cafego.backend.models.entities

import jakarta.persistence.*

@Entity
@Table(name = "invoices")
data class Invoice(

    @Column(nullable = false)
    val total: Double = 0.0,

    // Relación: Una factura pertenece a UN usuario
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    // Relación: Una factura tiene MUCHOS detalles
    // (mappedBy = "invoice" significa que la tabla 'invoice_details' es la dueña de la relación)
    @OneToMany(mappedBy = "invoice", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val details: MutableList<InvoiceDetail> = mutableListOf()

) : BaseEntity()