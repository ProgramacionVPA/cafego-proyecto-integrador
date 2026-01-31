package com.cafego.backend.mappers

import com.cafego.backend.controllers.UserIdentifyRequest
import com.cafego.backend.models.entities.User
import com.cafego.backend.models.responses.UserResponse
import org.springframework.stereotype.Component

@Component
class UserMapper {

    // Salida
    fun toResponse(entity: User): UserResponse {
        return UserResponse(
            id = entity.id ?: 0,
            fullName = entity.fullName,
            email = entity.email,
            cedula = entity.cedula
        )
    }

    // Entrada
    fun toEntity(request: UserIdentifyRequest): User {
        return User(
            cedula = request.cedula,
            fullName = request.fullName,
            email = request.email
        )
    }
}