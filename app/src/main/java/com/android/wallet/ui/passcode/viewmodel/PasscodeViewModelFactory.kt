package com.android.wallet.ui.passcode.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.wallet.repository.passcode.PasscodeRepository

class PasscodeViewModelFactory(private val repository: PasscodeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PasscodeViewModel(repository) as T
    }
}