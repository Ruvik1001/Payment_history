package com.example.domain.model

/**
 * Data class representing an error.
 *
 * @property error_code The error code associated with the error.
 * @property error_msg A human-readable error message describing the error.
 */
data class AuthError(
    val error_code: Int,
    val error_msg: String
)
