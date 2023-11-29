package com.example.paymenthistory.data

/**
 * Data class representing user credentials.
 *
 * @property login The user's login or username.
 * @property password The user's password.
 */
data class User(
    var login: String = "",
    var password: String = ""
)
