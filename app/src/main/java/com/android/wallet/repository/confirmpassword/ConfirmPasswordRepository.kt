package com.android.wallet.repository.confirmpassword

import android.content.Context
import com.android.wallet.network.ApiState
import com.android.wallet.repository.RepositoryBase
import com.android.wallet.model.confirmpassword.request.ConfirmPasswordRequest
import com.android.wallet.utils.AppUtils

class ConfirmPasswordRepository(val context: Context) : RepositoryBase(context) {

    // Confirm Password API
    suspend inline fun <reified T> verifyPasswordApi(
        confirmPasswordRequest: ConfirmPasswordRequest,
        isSuccessMessageShow: Boolean = true,
        isFailureMessageShow: Boolean = true,
    ): ApiState<T> {
        val apiCall = { apiClient.verifyPasswordApi(confirmPasswordRequest) }

        return AppUtils.getDataFromDatabaseOrApiLogic<T>(
            context,
            apiCall,
            isFailureMessageShow,
            isSuccessMessageShow,
        )
    }
}