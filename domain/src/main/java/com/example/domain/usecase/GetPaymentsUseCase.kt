package com.example.domain.usecase

import com.example.domain.model.PaymentResponse
import com.example.domain.repository.PaymentRepository

/**
 * Use case class for handling payment-related operations.
 *
 * @property paymentRepository The repository for managing payment-related data.
 */
class GetPaymentsUseCase(private val paymentRepository: PaymentRepository) {
    /**
     * Executes the use case to load the payment history for the authenticated user.
     *
     * @param token The user's authentication token.
     * @return A [PaymentResponse] containing the user's payment history.
     */
    suspend fun execute(token: String): PaymentResponse {
        return paymentRepository.loadPaymentHistory(token = token)
    }
}
