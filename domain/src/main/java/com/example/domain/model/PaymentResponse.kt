package com.example.domain.model

/**
 * Data class representing the response from a payment history request.
 *
 * @property success Indicates whether the request for payment history was successful.
 * @property response The list of payments retrieved in the response, may be `null` if the request is unsuccessful.
 * @property error Information about the error in case the request for payment history is unsuccessful, may be `null` if the request is successful.
 */
data class PaymentResponse(
    val success: Boolean,
    val response: List<Payment?>?,
    val error: AuthError?
)
