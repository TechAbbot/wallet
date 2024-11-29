package com.android.wallet.ui.confirmpassword.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.wallet.network.ApiState
import com.android.wallet.repository.confirmpassword.ConfirmPasswordRepository
import com.android.wallet.ui.base.BaseViewModel
import com.android.wallet.model.confirmpassword.request.ConfirmPasswordRequest
import kotlinx.coroutines.launch

class ConfirmPasswordViewModel(
    val repository: ConfirmPasswordRepository
) : BaseViewModel() {

    private val _confirmPasswordNewPasswordResponse = MutableLiveData<ApiState<*>>()
    val confirmPasswordNewPasswordResponse get() = _confirmPasswordNewPasswordResponse
    fun updateConfirmPasswordResponse(response: ApiState<*>) {
        _confirmPasswordNewPasswordResponse.value = response
    }

    inline fun <reified T> verifyPasswordApi(confirmPasswordRequest: ConfirmPasswordRequest) {
        viewModelScope.launch {
            val response = repository.verifyPasswordApi<T>(confirmPasswordRequest)
            updateConfirmPasswordResponse(response)
        }
    }
}