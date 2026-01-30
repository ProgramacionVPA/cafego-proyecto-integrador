package com.cafego.backend.repositories

import com.cafego.backend.models.entities.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TagRepository : JpaRepository<Tag, Long> {
    // Buscamos por nombre para ver si ya existe (Ej: "Caliente")
    fun findByName(name: String): Optional<Tag>
}