package com.example.domain.model

/**
 * Data class representing the response from an authentication request.
 *
 * @property success Indicates whether the authentication was successful.
 * @property response The authentication response data, may be `null` if the authentication is unsuccessful.
 * @property error Information about authentication error, may be `null` if the authentication is successful.
 */
data class AuthResponse(
    val success: Boolean,
    val response: AuthResponseData?,
    val error: AuthError?
)
