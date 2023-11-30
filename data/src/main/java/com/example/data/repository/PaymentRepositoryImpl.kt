package com.example.data.repository

import android.util.Log
import com.example.data.R
import com.example.data.api.ApiService
import com.example.data.special.Network
import com.example.domain.model.AuthError
import com.example.domain.model.PaymentResponse
import com.example.domain.repository.PaymentRepository

/**
 * Implementation of the [PaymentRepository] interface for managing payment-related data.
 *
 * @property apiService The ApiService for making API requests.
 * @property network The Network utility for checking network availability.
 */
class PaymentRepositoryImpl(private val apiService: ApiService, private val network: Network) :
    PaymentRepository {

    /**
     * Loads the payment history for the authenticated user.
     *
     * @param token The user's authentication token.
     * @return A [PaymentResponse] containing the user's payment history.
     */
    override suspend fun loadPaymentHistory(token: String): PaymentResponse {
        return try {
            apiService.getPayments(token = token).body()!!
        } catch (e: Exception) {
            val net = network.isNetworkAvailable()
            Log.e("PAY_HISTORY", e.message.toString())
            PaymentResponse(
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
