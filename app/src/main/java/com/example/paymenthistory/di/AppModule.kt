package com.example.paymenthistory.di

import com.example.domain.usecase.AuthUseCase
import com.example.domain.usecase.GetPaymentsUseCase
import com.example.paymenthistory.presentation.fragment.auth.AuthViewModel
import com.example.paymenthistory.presentation.fragment.history.HistoryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for providing ViewModel instances.
 */
val appModule = module {
    /**
     * ViewModel for handling authentication.
     */
    viewModel<AuthViewModel> {
        AuthViewModel(
            context = androidContext(),
            authUseCase = get<AuthUseCase>()
        )
    }

    /**
     * ViewModel for managing payment history data.
     */
    viewModel<HistoryViewModel> {
        HistoryViewModel(
            context = androidContext(),
            getPaymentsUseCase = get<GetPaymentsUseCase>()
        )
    }
}
