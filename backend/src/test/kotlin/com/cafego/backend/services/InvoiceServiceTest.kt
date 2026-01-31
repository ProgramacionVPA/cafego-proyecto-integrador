package com.cafego.backend.services

import com.cafego.backend.exceptions.ProductNotFoundException
import com.cafego.backend.exceptions.StockOutOfRangeException
import com.cafego.backend.mappers.InvoiceMapper
import com.cafego.backend.models.entities.Invoice
import com.cafego.backend.models.entities.Product
import com.cafego.backend.models.entities.User
import com.cafego.backend.models.requests.InvoiceItemRequest
import com.cafego.backend.models.requests.InvoiceRequest
import com.cafego.backend.repositories.InvoiceRepository
import com.cafego.backend.repositories.ProductRepository
import com.cafego.backend.repositories.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import java.util.Optional

class InvoiceServiceTest {

    private lateinit var invoiceRepository: InvoiceRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var userRepository: UserRepository
    private lateinit var invoiceMapper: InvoiceMapper
    private lateinit var invoiceService: InvoiceService

    @BeforeEach
    fun setUp() {
        invoiceRepository = mock(InvoiceRepository::class.java)
        productRepository = mock(ProductRepository::class.java)
        userRepository = mock(UserRepository::class.java)
        invoiceMapper = InvoiceMapper() // Usamos el mapper real

        invoiceService = InvoiceService(
            invoiceRepository = invoiceRepository,
            productRepository = productRepository,
            userRepository = userRepository,
            invoiceMapper = invoiceMapper
        )
    }

    @Test
    fun `SHOULD create invoice and reduce stock GIVEN valid request`() {
        // GIVEN
        val userId = 1L
        val productId = 100L

        val user = User("1700", "Pepe", "mail").apply { id = userId }
        val product = Product("Cafe", "Desc", 2.0, 10).apply { id = productId }

        val itemRequest = InvoiceItemRequest(productId = productId, quantity = 2)
        val request = InvoiceRequest(
            userId = userId,
            items = listOf(itemRequest)
        )

        val invoiceSaved = Invoice(
            total = 4.0,
            user = user,
            status = "PENDIENTE"
        ).apply { id = 10L }

        // Mocks
        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))
        `when`(productRepository.findById(productId)).thenReturn(Optional.of(product))

        // 👇👇👇 ¡AQUÍ ESTÁ LA SOLUCIÓN! 👇👇👇
        `when`(productRepository.save(any(Product::class.java))).thenAnswer { it.getArgument(0) }
        // 👆👆👆 (Sin esto, devolvía null y tu código fallaba)

        `when`(invoiceRepository.save(any(Invoice::class.java))).thenReturn(invoiceSaved)

        // WHEN
        val result = invoiceService.createInvoice(request)

        // THEN
        assertEquals(10L, result.id)
        verify(productRepository, times(1)).save(product)
    }

    @Test
    fun `SHOULD throw StockOutOfRangeException GIVEN quantity exceeds stock`() {
        // GIVEN
        val userId = 1L
        val productId = 100L

        val user = User("1700", "Pepe", "mail")
        val product = Product("Cafe", "Desc", 2.0, 5) // Solo hay 5

        // Pedimos 10
        val itemRequest = InvoiceItemRequest(productId = productId, quantity = 10)
        val request = InvoiceRequest(userId, listOf(itemRequest))

        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))
        `when`(productRepository.findById(productId)).thenReturn(Optional.of(product))

        // WHEN & THEN
        assertThrows<StockOutOfRangeException> {
            invoiceService.createInvoice(request)
        }
    }

    @Test
    fun `SHOULD dispatch invoice GIVEN valid ID`() {
        // GIVEN
        val invoiceId = 1L
        val user = User("1", "A", "b")

        // CORRECCIÓN CLAVE: Constructor correcto
        val invoice = Invoice(
            total = 10.0,
            user = user,
            status = "PENDIENTE"
        ).apply {
            id = invoiceId
        }

        `when`(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice))
        // Simulamos que al guardar, devuelve la misma factura (ya modificada)
        `when`(invoiceRepository.save(any(Invoice::class.java))).thenAnswer { it.getArgument(0) }

        // WHEN
        val result = invoiceService.dispatchInvoice(invoiceId)

        // THEN
        assertEquals("DESPACHADO", result.status)
    }

    @Test
    fun `SHOULD throw ProductNotFoundException GIVEN invalid invoice ID on dispatch`() {
        // Mock: No encuentra nada
        `when`(invoiceRepository.findById(99L)).thenReturn(Optional.empty())

        // THEN
        assertThrows<ProductNotFoundException> {
            invoiceService.dispatchInvoice(99L)
        }
    }

    @Test
    fun `SHOULD return pending invoices list`() {
        // GIVEN
        val user = User("1700", "Pepe", "mail")
        // Creamos una factura ficticia
        val invoice = Invoice(
            total = 5.0,
            user = user,
            status = "PENDIENTE"
        ).apply { id = 1L }

        // Mock: Cuando busque pendientes, devuelve nuestra lista
        `when`(invoiceRepository.findByStatus("PENDIENTE")).thenReturn(listOf(invoice))

        // WHEN
        val result = invoiceService.getPendingInvoices()

        // THEN
        assertEquals(1, result.size) // Debe haber 1 factura
        assertEquals(1L, result[0].id) // Debe ser la ID 1
    }
}