package com.cafego.backend.exceptions

class ProductAlreadyExistsException(
    message: String,
) : RuntimeException(message)