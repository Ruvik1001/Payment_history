package com.example.data.repository

import com.example.data.R
import com.example.data.api.ApiService
import com.example.data.special.Network
import com.example.domain.model.AuthError
import com.example.domain.model.AuthResponse
import com.example.domain.model.User
import com.example.domain.repository.UserRepository

/**
 * Implementation of the [UserRepository] interface for managing user-related data.
 *
 * @property apiService The ApiService for making API requests.
 * @property network The Network utility for checking network availability.
 */
class UserRepositoryImpl(private val apiService: ApiService, private val network: Network) :
    UserRepository {

    /**
     * Authenticates the user using the provided credentials.
     *
     * @param user The user credentials for authentication.
     * @return An [AuthResponse] indicating the authentication status.
     */
    override suspend fun auth(user: User): AuthResponse {
        return try {
            apiService.login(user).body()!!
        } catch (e: Exception) {
            val net = network.isNetworkAvailable()
            AuthResponse(
                success = false,
                response = null,
                error = AuthError(
                    error_code = if (net) 408 else 403,
                    error_msg = if (net) network.getContext().getString(R.string.request_timeout_408)
                    else network.getContext().getString(R.string.network_error_404)
                )
            )
        }
    }
}
