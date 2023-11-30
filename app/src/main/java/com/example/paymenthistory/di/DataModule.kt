package com.example.paymenthistory.di

import com.example.data.api.ApiService
import com.example.data.repository.PaymentRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.data.special.Network
import com.example.domain.repository.PaymentRepository
import com.example.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Koin module for providing data-related dependencies.
 */
val dataModule = module {
    /**
     * Provides a Retrofit instance for making API requests.
     */
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://easypay.world/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides an instance of the ApiService interface for handling API requests.
     */
    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

    /**
     * Provides a Network instance for checking network availability.
     */
    single<Network> {
        Network(androidContext())
    }

    /**
     * Provides a PaymentRepository implementation for managing payment-related data.
     */
    single<PaymentRepository> {
        PaymentRepositoryImpl(
            apiService = get<ApiService>(),
            network = get<Network>()
        )
    }

    /**
     * Provides a UserRepository implementation for managing user-related data.
     */
    single<UserRepository> {
        UserRepositoryImpl(
            apiService = get<ApiService>(),
            network = get<Network>()
        )
    }
}
