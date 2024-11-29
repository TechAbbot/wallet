package com.android.wallet.ui.forgotpassword.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.wallet.network.ApiState
import com.android.wallet.repository.forgotpassword.ForgotPasswordRepository
import com.android.wallet.ui.base.BaseViewModel
import com.android.wallet.model.forgotpassword.request.ForgotPasswordRequest
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    val repository: ForgotPasswordRepository
) : BaseViewModel() {

    val _forgotPasswordEmailResponse: MutableLiveData<ApiState<*>> = MutableLiveData()
    val forgotPasswordEmailResponse: LiveData<ApiState<*>>
        get() {
            return _forgotPasswordEmailResponse
        }


    val _forgotPasswordCodeResponse: MutableLiveData<ApiState<*>> = MutableLiveData()
    val forgotPasswordCodeResponse: LiveData<ApiState<*>>
        get() {
            return _forgotPasswordCodeResponse
        }


    val _forgotPasswordNewPasswordResponse: MutableLiveData<ApiState<*>> = MutableLiveData()
    val forgotPasswordNewPasswordResponse: LiveData<ApiState<*>>
        get() {
            return _forgotPasswordNewPasswordResponse
        }

    inline fun <reified T> sendPasswordResetEmailApi(forgotPasswordRequest: ForgotPasswordRequest) {
        viewModelScope.launch {
            val response = repository.sendPasswordResetEmailApi<T>(forgotPasswordRequest)
            launch {
                _forgotPasswordEmailResponse.postValue(response)
            }
        }
    }

    inline fun <reified T> tokenValidationApi(forgotPasswordRequest: ForgotPasswordRequest) {
        viewModelScope.launch {
            val response = repository.tokenValidationApi<T>(forgotPasswordRequest)
            launch {
                _forgotPasswordCodeResponse.postValue(response)
            }
        }
    }

    inline fun <reified T> resetForgotPasswordApi(forgotPasswordRequest: ForgotPasswordRequest) {
        viewModelScope.launch {
            val response = repository.resetForgotPasswordApi<T>(forgotPasswordRequest)
            launch {
                _forgotPasswordNewPasswordResponse.postValue(response)
            }
        }
    }
}