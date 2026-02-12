package com.cafego.app.models

object CartManager {
    val products = mutableListOf<Product>()

    // Cambiamos cedulaUsuario por userIdLogueado
    var userIdLogueado: Long = 0

    fun addProduct(product: Product) {
        products.add(product)
    }

    fun clearCart() {
        products.clear()
    }

    fun getTotal(): Double {
        return products.sumOf { it.price }
    }
    fun getItemsCount(): Int {
        return products.size
    }


}