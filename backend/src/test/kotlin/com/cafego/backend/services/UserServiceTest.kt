package com.cafego.backend.services

import com.cafego.backend.mappers.UserMapper
import com.cafego.backend.models.entities.User
import com.cafego.backend.repositories.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import java.util.Optional

class UserServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var userMapper: UserMapper
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        userRepository = mock(UserRepository::class.java)
        userMapper = UserMapper() // Usamos el mapper real

        userService = UserService(
            userRepository = userRepository,
            userMapper = userMapper
        )
    }

    @Test
    fun `SHOULD create and return new user GIVEN a non-existing cedula`() {
        // GIVEN
        val cedula = "1720000000"
        val userSaved = User(cedula, "Victor Nuevo", "mail@test.com").apply { id = 1L }

        `when`(userRepository.findByCedula(cedula)).thenReturn(Optional.empty())
        `when`(userRepository.save(any(User::class.java))).thenReturn(userSaved)

        // WHEN
        val result = userService.identifyUser(cedula, "Victor Nuevo", "mail@test.com")

        // THEN
        assertEquals(1L, result.id)
        assertEquals("Victor Nuevo", result.fullName)
    }

    @Test
    fun `SHOULD return existing user GIVEN an existing cedula`() {
        // GIVEN
        val cedula = "1720000000"
        val existingUser = User(cedula, "Victor Viejo", "old@test.com").apply { id = 5L }

        `when`(userRepository.findByCedula(cedula)).thenReturn(Optional.of(existingUser))

        // WHEN
        val result = userService.identifyUser(cedula, "Cualquier Nombre", "mail@test.com")

        // THEN
        assertEquals(5L, result.id)
        assertEquals("Victor Viejo", result.fullName)
    }

    @Test
    fun `SHOULD return all users list`() {
        // GIVEN
        val user1 = User("171", "Pepe", "a@a.com")
        val user2 = User("172", "Juan", "b@b.com")

        // Simulamos que la base de datos devuelve 2 usuarios
        `when`(userRepository.findAll()).thenReturn(listOf(user1, user2))

        // WHEN
        val result = userService.findAll()

        // THEN
        assertEquals(2, result.size)
        // Verificamos que realmente se llamó a la base de datos
        verify(userRepository, times(1)).findAll()
    }
}