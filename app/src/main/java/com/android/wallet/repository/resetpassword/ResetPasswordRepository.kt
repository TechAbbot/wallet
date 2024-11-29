package com.android.wallet.repository.resetpassword

import android.content.Context
import com.android.wallet.network.ApiState
import com.android.wallet.repository.RepositoryBase
import com.android.wallet.model.resetpassword.request.ResetPasswordRequest
import com.android.wallet.utils.AppUtils

class ResetPasswordRepository(val context: Context) : RepositoryBase(context) {
    // Reset Password API
    suspend inline fun <reified T> resetPasswordApi(
        resetPasswordRequest: ResetPasswordRequest,
        isSuccessMessageShow: Boolean = true,
        isFailureMessageShow: Boolean = true,
    ): ApiState<T> {
        val apiCall = { apiClient.resetPasswordApi(resetPasswordRequest) }

        return AppUtils.getDataFromDatabaseOrApiLogic<T>(
            context,
            apiCall,
            isFailureMessageShow,
            isSuccessMessageShow,
        )
    }
}