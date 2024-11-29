package com.android.wallet.repository.login

import android.content.Context
import com.android.wallet.network.ApiState
import com.android.wallet.repository.RepositoryBase
import com.android.wallet.model.login.request.LoginRequest
import com.android.wallet.utils.AppUtils

class LoginRepository(val context: Context) : RepositoryBase(context) {
    // Login API
    suspend inline fun <reified T> loginApi(
        loginRequest: LoginRequest,
        isSuccessMessageShow: Boolean = false,
        isFailureMessageShow: Boolean = true,
    ): ApiState<T> {
        val apiCall = { apiClient.loginApi(loginRequest) }

        return AppUtils.getDataFromDatabaseOrApiLogic<T>(
            context,
            apiCall,
            isFailureMessageShow,
            isSuccessMessageShow,
        )
    }
}