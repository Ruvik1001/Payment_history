package com.example.domain.model

/**
 * Data class representing the response data for a successful authentication.
 *
 * @property token The authentication token associated with the authenticated user.
 */
data class AuthResponseData(
    val token: String
)
