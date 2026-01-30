package com.cafego.backend.repositories

import com.cafego.backend.models.entities.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    // NOTA: No agregamos 'findByName' porque el profe pidió quitarla por ahora.
    // Más adelante, aquí agregaremos la "Búsqueda Doble" (Name + Tags).
}