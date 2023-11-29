package com.example.paymenthistory.presentation.fragment.history

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.paymenthistory.R
import com.example.paymenthistory.api.ApiService
import com.example.paymenthistory.data.AuthError
import com.example.paymenthistory.data.AuthResponse
import com.example.paymenthistory.data.PaymentResponse
import com.example.paymenthistory.special.isNetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ViewModel for managing payment history data.
 *
 * @property context The application context.
 * @property paymentResponseMutableLiveData MutableLiveData to hold the payment response data.
 * @property paymentResponseLiveData LiveData to observe the payment response data.
 * @property apiService The Retrofit service for handling API requests.
 */
class HistoryViewModel(private val context: Context) : ViewModel() {
    private val paymentResponseMutableLiveData = MutableLiveData<PaymentResponse>()
    val paymentResponseLiveData: LiveData<PaymentResponse> = paymentResponseMutableLiveData

    private val apiService: ApiService = Retrofit.Builder()
        .baseUrl("https://easypay.world/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    /**
     * Loads payment history data from the API.
     *
     * @param token The authentication token.
     */
    fun load(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val paymentResponse: PaymentResponse = try {
                val t = apiService.getPayments(token = token)
                t.body()!!
            } catch (e: Exception) {
                val net = isNetworkAvailable(context)
                Log.e("PAY_HISTORY", e.message.toString())
                PaymentResponse(false, null, AuthError(
                    error_code = if (net) 408 else 403,
                    error_msg = if (net) context.getString(R.string.request_timeout_408)
                    else context.getString(R.string.network_error_404)
                ))
            }
            withContext(Dispatchers.Main) {
                paymentResponseMutableLiveData.postValue(paymentResponse)
            }
        }
    }
}
