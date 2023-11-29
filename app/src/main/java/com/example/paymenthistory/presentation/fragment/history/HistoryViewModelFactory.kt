package com.example.paymenthistory.presentation.fragment.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory class for creating an instance of [HistoryViewModel].
 *
 * @property context The application context.
 */
class HistoryViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    /**
     * Creates an instance of the specified [ViewModel].
     *
     * @param modelClass The class of the [ViewModel] to create.
     * @return A new instance of the specified [ViewModel].
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistoryViewModel(context = context) as T
    }
}
