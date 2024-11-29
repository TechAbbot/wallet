package com.android.wallet.ui.resetpassword.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.wallet.repository.resetpassword.ResetPasswordRepository

class ResetPasswordViewModelFactory(private val repository: ResetPasswordRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ResetPasswordViewModel(repository) as T
    }
}