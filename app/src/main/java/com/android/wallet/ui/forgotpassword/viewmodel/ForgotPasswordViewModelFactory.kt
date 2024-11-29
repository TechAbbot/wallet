package com.android.wallet.ui.forgotpassword.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.wallet.repository.forgotpassword.ForgotPasswordRepository

class ForgotPasswordViewModelFactory(private val repository: ForgotPasswordRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ForgotPasswordViewModel(repository) as T
    }
}