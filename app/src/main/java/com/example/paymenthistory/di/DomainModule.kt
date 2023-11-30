package com.example.paymenthistory.di

import com.example.domain.repository.PaymentRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.AuthUseCase
import com.example.domain.usecase.GetPaymentsUseCase
import org.koin.dsl.module

/**
 * Koin module for providing domain-related dependencies.
 */
val domainModule = module {
    /**
     * Provides an instance of AuthUseCase for handling authentication-related use cases.
     */
    factory<AuthUseCase> {
        AuthUseCase(userRepository = get<UserRepository>())
    }

    /**
     * Provides an instance of GetPaymentsUseCase for handling payment-related use cases.
     */
    factory<GetPaymentsUseCase> {
        GetPaymentsUseCase(paymentRepository = get<PaymentRepository>())
    }
}
