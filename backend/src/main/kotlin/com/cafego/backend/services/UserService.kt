package com.cafego.backend.services

import com.cafego.backend.models.entities.User
import com.cafego.backend.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun identifyUser(cedula: String, fullName: String, email: String): User {
        // 1. Buscamos si ya existe
        val existingUser = userRepository.findByCedula(cedula)

        return if (existingUser.isPresent) {
            // Si existe, lo devolvemos
            existingUser.get()
        } else {
            // Si no, lo creamos
            val newUser = User(
                cedula = cedula,
                fullName = fullName,
                email = email
            )
            userRepository.save(newUser)
        }
    }
}