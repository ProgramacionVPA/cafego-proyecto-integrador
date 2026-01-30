package com.cafego.backend.services

import com.cafego.backend.exceptions.ProductNotFoundException
import com.cafego.backend.exceptions.StockOutOfRangeException
import com.cafego.backend.mappers.InvoiceMapper
import com.cafego.backend.models.entities.Invoice
import com.cafego.backend.models.entities.InvoiceDetail
import com.cafego.backend.models.requests.InvoiceRequest
import com.cafego.backend.models.responses.InvoiceResponse
import com.cafego.backend.repositories.InvoiceRepository
import com.cafego.backend.repositories.ProductRepository
import com.cafego.backend.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InvoiceService(
    private val invoiceRepository: InvoiceRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
    private val invoiceMapper: InvoiceMapper
) {

    @Transactional
    fun createInvoice(request: InvoiceRequest): InvoiceResponse {
        // 1. Buscar Usuario
        val user = userRepository.findById(request.userId)
            .orElseThrow { ProductNotFoundException("El usuario con id ${request.userId} no existe") }

        // 2. PRIMERA PASADA: Calcular Total y Validar
        var calculatedTotal = 0.0

        // Lista temporal para guardar los pares (Producto, Cantidad)
        val itemsToProcess = request.items.map { item ->
            val product = productRepository.findById(item.productId)
                .orElseThrow { ProductNotFoundException("Producto con id ${item.productId} no encontrado") }

            if (product.stock < item.quantity) {
                throw StockOutOfRangeException("No hay stock suficiente para ${product.name}")
            }

            calculatedTotal += (product.price * item.quantity)
            Pair(product, item.quantity)
        }

        // 3. Crear la Factura
        val invoice = Invoice(total = calculatedTotal, user = user)

        // 4. SEGUNDA PASADA: Descontar Stock y Guardar
        itemsToProcess.forEach { (product, quantity) ->

            // A. Descontar Stock DIRECTAMENTE (Gracias al 'var')
            product.stock = product.stock - quantity
            val productUpdated = productRepository.save(product) // Guardamos el MISMO objeto

            // B. Crear Detalle
            val detail = InvoiceDetail(
                quantity = quantity,
                unitPrice = productUpdated.price,
                product = productUpdated,
                invoice = invoice
            )

            invoice.details.add(detail)
        }

        // 5. Guardar Factura Final
        val savedInvoice = invoiceRepository.save(invoice)

        return invoiceMapper.toResponse(savedInvoice)
    }
}