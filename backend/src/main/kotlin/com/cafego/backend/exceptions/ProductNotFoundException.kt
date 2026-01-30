package com.cafego.backend.exceptions

class ProductNotFoundException(
    message: String,
) : RuntimeException(message)