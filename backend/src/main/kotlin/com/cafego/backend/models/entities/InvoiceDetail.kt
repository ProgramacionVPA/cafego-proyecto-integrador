package com.cafego.backend.models.entities

import jakarta.persistence.*

@Entity
@Table(name = "invoice_details")
data class InvoiceDetail(

    @Column(nullable = false)
    val quantity: Int,

    // Guardamos el precio al momento de la venta
    // (por si el producto sube de precio mañana, la factura vieja no cambia)
    @Column(name = "unit_price", nullable = false)
    val unitPrice: Double,

    // Relación: Un detalle pertenece a UNA factura
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    var invoice: Invoice? = null,

    // Relación: Un detalle pertenece a UN producto
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product

) : BaseEntity()