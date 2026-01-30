package com.cafego.backend.repositories

import com.cafego.backend.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {
    // Para el login o validaciones futuras
    fun findByEmail(email: String): Optional<User>
}