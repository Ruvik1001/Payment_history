package com.example.domain.repository

import com.example.domain.model.PaymentResponse

/**
 * Repository interface for managing payment-related data.
 */
interface PaymentRepository {
    /**
     * Loads the payment history for the authenticated user.
     *
     * @param token The user's authentication token.
     * @return A [PaymentResponse] containing the user's payment history.
     */
    suspend fun loadPaymentHistory(token: String): PaymentResponse
}
