package com.cafego.backend.repositories

import com.cafego.backend.models.entities.Invoice
import org.springframework.data.jpa.repository.JpaRepository

interface InvoiceRepository : JpaRepository<Invoice, Long> {
    // Busca todas las facturas que tengan el estado X (ej: "PENDIENTE")
    fun findByStatus(status: String): List<Invoice>
}