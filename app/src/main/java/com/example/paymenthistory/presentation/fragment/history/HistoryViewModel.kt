package com.example.paymenthistory.presentation.fragment.history

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.PaymentResponse
import com.example.domain.usecase.GetPaymentsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for managing payment history data.
 *
 * @property context The application context.
 * @property paymentResponseMutableLiveData MutableLiveData to hold the payment response data.
 * @property paymentResponseLiveData LiveData to observe the payment response data.
 */
class HistoryViewModel(
    private val context: Context,
    private val getPaymentsUseCase: GetPaymentsUseCase) : ViewModel() {
    private val paymentResponseMutableLiveData = MutableLiveData<PaymentResponse>()
    val paymentResponseLiveData: LiveData<PaymentResponse> = paymentResponseMutableLiveData

//    private val apiService: ApiService = Retrofit.Builder()
//        .baseUrl("https://easypay.world/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//        .create(ApiService::class.java)

    /**
     * Loads payment history data from the API.
     *
     * @param token The authentication token.
     */
    fun load(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val paymentResponse: PaymentResponse = getPaymentsUseCase.execute(token = token)
            withContext(Dispatchers.Main) {
                paymentResponseMutableLiveData.postValue(paymentResponse)
            }
        }
    }
}
