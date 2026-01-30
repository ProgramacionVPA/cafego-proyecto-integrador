package com.cafego.backend.exceptions

class StockOutOfRangeException(
    message: String,
) : RuntimeException(message)