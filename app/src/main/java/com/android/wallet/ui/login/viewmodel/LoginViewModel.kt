package com.android.wallet.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.wallet.network.ApiState
import com.android.wallet.repository.login.LoginRepository
import com.android.wallet.ui.base.BaseViewModel
import com.android.wallet.model.login.request.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel(
    val repository: LoginRepository
) : BaseViewModel() {

    val _loginUserResponse: MutableLiveData<ApiState<*>> = MutableLiveData()
    val loginUserResponse: LiveData<ApiState<*>>
        get() {
            return _loginUserResponse
        }

    inline fun <reified T> loginApi(loginRequest: LoginRequest) {
        viewModelScope.launch {
            val response = repository.loginApi<T>(loginRequest)
            launch {
                _loginUserResponse.postValue(response)
            }
        }
    }
}