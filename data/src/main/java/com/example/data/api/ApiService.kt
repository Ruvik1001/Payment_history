package com.example.data.api

import com.example.domain.model.AuthResponse
import com.example.domain.model.PaymentResponse
import com.example.domain.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Retrofit API interface for handling authentication and payment-related requests.
 */
interface ApiService {
    /**
     * Performs user authentication.
     *
     * @param loginRequest The user credentials for authentication.
     * @param appKey The application key header.
     * @param version The API version header.
     * @return A [Response] containing an [AuthResponse] indicating the authentication status.
     */
    @POST(API_LOGIN_PATH)
    suspend fun login(
        @Body loginRequest: User,
        @Header("app-key") appKey: String = DEFAULT_APP_KEY,
        @Header("v") version: String = DEFAULT_API_VERSION
    ): Response<AuthResponse>

    /**
     * Retrieves the list of payments for the authenticated user.
     *
     * @param token The user's authentication token.
     * @param appKey The application key header.
     * @param version The API version header.
     * @return A [Response] containing a [PaymentResponse] with the user's payment history.
     */
    @GET(API_PAYMENTS_PATH)
    suspend fun getPayments(
        @Header("token") token: String,
        @Header("app-key") appKey: String = DEFAULT_APP_KEY,
        @Header("v") version: String = DEFAULT_API_VERSION
    ): Response<PaymentResponse>

    companion object {
        const val API_LOGIN_PATH = "/api-test/login"
        const val API_PAYMENTS_PATH = "/api-test/payments"
        const val DEFAULT_APP_KEY = "12345"
        const val DEFAULT_API_VERSION = "1"
    }
}
