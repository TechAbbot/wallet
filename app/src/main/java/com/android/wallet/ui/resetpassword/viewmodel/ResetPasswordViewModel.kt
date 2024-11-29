package com.android.wallet.ui.resetpassword.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.wallet.network.ApiState
import com.android.wallet.repository.resetpassword.ResetPasswordRepository
import com.android.wallet.ui.base.BaseViewModel
import com.android.wallet.model.resetpassword.request.ResetPasswordRequest
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    val repository: ResetPasswordRepository
) : BaseViewModel() {

    val _resetPasswordNewPasswordResponse: MutableLiveData<ApiState<*>> = MutableLiveData()
    val resetPasswordNewPasswordResponse: LiveData<ApiState<*>>
        get() {
            return _resetPasswordNewPasswordResponse
        }

    inline fun <reified T> resetPasswordApi(resetPasswordRequest: ResetPasswordRequest) {
        viewModelScope.launch {
            val response = repository.resetPasswordApi<T>(resetPasswordRequest)
            launch {
                _resetPasswordNewPasswordResponse.postValue(response)
            }
        }
    }
}