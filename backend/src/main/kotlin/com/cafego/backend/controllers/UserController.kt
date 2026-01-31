package com.cafego.backend.controllers

import com.cafego.backend.models.responses.UserResponse
import com.cafego.backend.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class UserIdentifyRequest(
    val cedula: String,
    val fullName: String,
    val email: String
)

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/identify")
    fun identifyUser(@RequestBody request: UserIdentifyRequest): UserResponse { // <--- Ahora devuelve UserResponse
        return userService.identifyUser(
            cedula = request.cedula,
            fullName = request.fullName,
            email = request.email
        )
    }

    @GetMapping
    fun getAllUsers(): List<UserResponse> {
        return userService.findAll()
    }
}