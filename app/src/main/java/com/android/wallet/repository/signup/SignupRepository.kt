package com.android.wallet.repository.signup

import android.content.Context
import com.android.wallet.network.ApiState
import com.android.wallet.repository.RepositoryBase
import com.android.wallet.model.signup.request.SignupRequest
import com.android.wallet.utils.AppUtils

class SignupRepository(val context: Context) : RepositoryBase(context) {
    // Signup API
    suspend inline fun <reified T> signupApi(
        signupRequest: SignupRequest,
        isSuccessMessageShow: Boolean = true,
        isFailureMessageShow: Boolean = true,
    ): ApiState<T> {
        val apiCall = { apiClient.signupApi(signupRequest) }

        return AppUtils.getDataFromDatabaseOrApiLogic<T>(
            context,
            apiCall,
            isFailureMessageShow,
            isSuccessMessageShow,
        )
    }
}