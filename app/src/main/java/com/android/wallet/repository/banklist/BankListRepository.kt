package com.android.wallet.repository.banklist

import android.content.Context
import com.android.wallet.network.ApiState
import com.android.wallet.repository.RepositoryBase
import com.android.wallet.utils.AppUtils

class BankListRepository(val context: Context) : RepositoryBase(context) {

    // Bank Name List API
    suspend inline fun <reified T> getBankNamesApi(
        isSuccessMessageShow: Boolean = false,
        isFailureMessageShow: Boolean = true,
    ): ApiState<T> {
        val apiCall = { apiClient.getBankNamesApi() }

        return AppUtils.getDataFromDatabaseOrApiLogic<T>(
            context,
            apiCall,
            isFailureMessageShow,
            isSuccessMessageShow,
        )
    }
}