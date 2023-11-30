package com.example.paymenthistory.presentation.fragment.auth

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.paymenthistory.R
import com.example.domain.model.AuthResponse
import com.example.domain.model.User
import com.example.domain.usecase.AuthUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for the authentication fragment.
 *
 * @property context The application context.
 * @property user The user data.
 * @property responseMutableLiveData MutableLiveData to hold the authentication response.
 * @property responseLiveData LiveData to observe the authentication response.
 */
class AuthViewModel(
    private val context: Context,
    private val authUseCase: AuthUseCase) : ViewModel() {
    private var user: User
    private val responseMutableLiveData = MutableLiveData<AuthResponse>()
    val responseLiveData: LiveData<AuthResponse> = responseMutableLiveData

//    private val apiService: ApiService = Retrofit.Builder()
//        .baseUrl("https://easypay.world/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//        .create(ApiService::class.java)

    init {
        user = User(
            login = context.getSharedPreferences(context.getString(R.string.DATA_FILE), Context.MODE_PRIVATE)
                .getString(context.getString(R.string.KEY_USER_LOGIN), "") ?: "",
            password = ""
        )
    }

    /**
     * Sets the user data for authentication.
     *
     * @param login The user login.
     * @param password The user password.
     */
    fun setUserData(login: String, password: String) {
        user.login = login
        user.password = password
    }

    /**
     * Retrieves the current user data.
     *
     * @return The user data.
     */
    fun getUserData(): User {
        return user
    }

    /**
     * Initiates the authentication process.
     */
    fun auth() {
        CoroutineScope(Dispatchers.IO).launch {
            val authResponse: AuthResponse = authUseCase.execute(user = user)
            withContext(Dispatchers.Main) {
                responseMutableLiveData.postValue(authResponse)
            }
            if (authResponse.success) {
                val sharedPreferences =
                    context.getSharedPreferences(context.getString(R.string.DATA_FILE), Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString(context.getString(R.string.KEY_USER_LOGIN), user.login)
                editor.apply()
            }
        }
    }
}
