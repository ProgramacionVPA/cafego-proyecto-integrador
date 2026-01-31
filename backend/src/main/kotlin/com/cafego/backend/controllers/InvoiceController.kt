package com.cafego.backend.controllers

import com.cafego.backend.models.requests.InvoiceRequest
import com.cafego.backend.models.responses.InvoiceResponse
import com.cafego.backend.services.InvoiceService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/invoices")
class InvoiceController(
    private val invoiceService: InvoiceService
) {

    // 1. CREAR FACTURA (Lo usa el Cliente desde su celular)
    @PostMapping
    fun create(@Valid @RequestBody request: InvoiceRequest): InvoiceResponse {
        return invoiceService.createInvoice(request)
    }

    // 2. VER PENDIENTES (Lo usa la Pantalla de Cocina)
    @GetMapping("/pending")
    fun getPending(): List<InvoiceResponse> {
        return invoiceService.getPendingInvoices()
    }

    // 3. DESPACHAR PEDIDO (Lo usa el botón de "Entregar")
    @PutMapping("/{id}/dispatch")
    fun dispatch(@PathVariable id: Long): InvoiceResponse {
        return invoiceService.dispatchInvoice(id)
    }
}