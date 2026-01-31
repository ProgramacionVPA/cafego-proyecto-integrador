package com.cafego.backend.services

import com.cafego.backend.mappers.UserMapper
import com.cafego.backend.models.responses.UserResponse
import com.cafego.backend.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {

    fun identifyUser(cedula: String, fullName: String, email: String): UserResponse {
        // 1. Buscamos si ya existe
        val existingUser = userRepository.findByCedula(cedula)

        val userEntity = if (existingUser.isPresent) {
            existingUser.get()
        } else {
            // 2. Si no existe, usamos el MAPPER para crearlo
            // Creamos un request temporal para pasárselo al mapper
            val tempRequest = com.cafego.backend.controllers.UserIdentifyRequest(cedula, fullName, email)

            // Usamos toEntity
            val newUser = userMapper.toEntity(tempRequest)

            userRepository.save(newUser)
        }

        return userMapper.toResponse(userEntity)
    }

    // Función para listar todos los usuarios (Solo para admins/debug)
    fun findAll(): List<UserResponse> {
        return userRepository.findAll()
            .map { userMapper.toResponse(it) }
    }
}