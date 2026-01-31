package com.cafego.backend.repositories

import com.cafego.backend.models.entities.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    // Para saber si ya existe el nombre
    fun existsByName(name: String): Boolean

    // Búsqueda Doble: Busca si el término coincide con el Nombre del producto O con el Nombre de algun Tag
    @Query("""
        SELECT DISTINCT p FROM Product p 
        LEFT JOIN p.tags t 
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :term, '%')) 
        OR LOWER(t.name) LIKE LOWER(CONCAT('%', :term, '%'))
    """)
    fun search(@Param("term") term: String): List<Product>
}