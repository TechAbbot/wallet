package com.android.wallet.repository.forgotpassword

import android.content.Context
import com.android.wallet.network.ApiState
import com.android.wallet.repository.RepositoryBase
import com.android.wallet.model.forgotpassword.request.ForgotPasswordRequest
import com.android.wallet.utils.AppUtils

class ForgotPasswordRepository(val context: Context) : RepositoryBase(context) {
    // Forgot Password Email API
    suspend inline fun <reified T> sendPasswordResetEmailApi(
        forgotPasswordRequest: ForgotPasswordRequest,
        isSuccessMessageShow: Boolean = true,
        isFailureMessageShow: Boolean = true,
    ): ApiState<T> {
        val apiCall = { apiClient.sendPasswordResetEmailApi(forgotPasswordRequest) }

        return AppUtils.getDataFromDatabaseOrApiLogic<T>(
            context,
            apiCall,
            isFailureMessageShow,
            isSuccessMessageShow,
        )
    }

    // Forgot Password Code API
    suspend inline fun <reified T> tokenValidationApi(
        forgotPasswordRequest: ForgotPasswordRequest,
        isSuccessMessageShow: Boolean = true,
        isFailureMessageShow: Boolean = true,
    ): ApiState<T> {
        val apiCall = { apiClient.tokenValidationApi(forgotPasswordRequest) }

        return AppUtils.getDataFromDatabaseOrApiLogic<T>(
            context,
            apiCall,
            isFailureMessageShow,
            isSuccessMessageShow,
        )
    }

    // Forgot Password New Password API
    suspend inline fun <reified T> resetForgotPasswordApi(
        forgotPasswordRequest: ForgotPasswordRequest,
        isSuccessMessageShow: Boolean = true,
        isFailureMessageShow: Boolean = true,
    ): ApiState<T> {
        val apiCall = { apiClient.resetForgotPasswordApi(forgotPasswordRequest) }

        return AppUtils.getDataFromDatabaseOrApiLogic<T>(
            context,
            apiCall,
            isFailureMessageShow,
            isSuccessMessageShow,
        )
    }
}