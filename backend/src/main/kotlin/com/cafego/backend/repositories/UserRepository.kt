package com.cafego.backend.repositories

import com.cafego.backend.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    // Esta es la magia: Spring crea el SQL solo con ver el nombre de la función
    fun findByCedula(cedula: String): Optional<User>
}