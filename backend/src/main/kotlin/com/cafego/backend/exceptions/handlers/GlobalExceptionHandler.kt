package com.cafego.backend.exceptions.handlers

import com.cafego.backend.exceptions.ProductAlreadyExistsException
import com.cafego.backend.exceptions.ProductNotFoundException
import com.cafego.backend.exceptions.StockOutOfRangeException
import com.cafego.backend.models.responses.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Manejador de excepciones
 * Traducir excepciones hacia codigos http
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    // 400 -> Bad Request (Stock incorrecto)
    @ExceptionHandler(StockOutOfRangeException::class)
    fun handleStockOutOfRangeException(
        ex: StockOutOfRangeException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ex.message),
            HttpStatus.BAD_REQUEST
        )
    }

    // 400 -> Bad Request (Producto ya existe)
    @ExceptionHandler(ProductAlreadyExistsException::class)
    fun handleProductAlreadyExistsException(
        ex: ProductAlreadyExistsException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ex.message),
            HttpStatus.BAD_REQUEST
        )
    }

    // 404 -> Not Found (No encontrado)
    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFoundException(
        ex: ProductNotFoundException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ex.message),
            HttpStatus.NOT_FOUND
        )
    }

    // Validación de campos (@NotBlank, @Min)
    // ES VITAL para que no salga error 500 si mandas precio negativo
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        // Toma el primer error que encuentre y lo muestra
        val errorMessage = ex.bindingResult.fieldErrors.firstOrNull()?.defaultMessage ?: "Error de validación"
        return ResponseEntity(
            ErrorResponse(errorMessage),
            HttpStatus.BAD_REQUEST
        )
    }
}