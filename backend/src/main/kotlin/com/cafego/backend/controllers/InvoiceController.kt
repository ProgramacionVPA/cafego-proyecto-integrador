package com.cafego.backend.controllers

import com.cafego.backend.models.requests.InvoiceRequest
import com.cafego.backend.models.responses.InvoiceResponse
import com.cafego.backend.services.InvoiceService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/invoices")
class InvoiceController(
    private val invoiceService: InvoiceService
) {

    @PostMapping
    fun create(@Valid @RequestBody request: InvoiceRequest): InvoiceResponse {
        return invoiceService.createInvoice(request)
    }
}