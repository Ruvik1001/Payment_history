package com.example.domain.usecase

import com.example.domain.model.AuthResponse
import com.example.domain.model.User
import com.example.domain.repository.UserRepository

/**
 * Use case class for handling user authentication.
 *
 * @property userRepository The repository for managing user-related data.
 */
class AuthUseCase(private val userRepository: UserRepository) {
    /**
     * Executes the authentication use case.
     *
     * @param user The user credentials for authentication.
     * @return An [AuthResponse] indicating the authentication status.
     */
    suspend fun execute(user: User): AuthResponse {
        return userRepository.auth(user = user)
    }
}
