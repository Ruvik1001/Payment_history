package com.example.domain.repository

import com.example.domain.model.AuthResponse
import com.example.domain.model.User

/**
 * Repository interface for managing user-related data.
 */
interface UserRepository {
    /**
     * Authenticates the user using the provided credentials.
     *
     * @param user The user credentials for authentication.
     * @return An [AuthResponse] indicating the authentication status.
     */
    suspend fun auth(user: User): AuthResponse
}
