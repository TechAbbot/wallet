package com.android.wallet.ui.resetpassword

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.android.wallet.R
import com.android.wallet.databinding.ActivityResetPasswordBinding
import com.android.wallet.model.login.LoginResponse
import com.android.wallet.model.resetpassword.request.ResetPasswordRequest
import com.android.wallet.network.Status
import com.android.wallet.repository.resetpassword.ResetPasswordRepository
import com.android.wallet.ui.base.BaseActivity
import com.android.wallet.ui.resetpassword.viewmodel.ResetPasswordViewModel
import com.android.wallet.ui.resetpassword.viewmodel.ResetPasswordViewModelFactory
import com.android.wallet.utils.AppConstants.MessageBackgroundColor.MESSAGE_COLOR_ERROR
import com.android.wallet.utils.AppUtils.doLogout
import com.android.wallet.utils.ExtensionUtils.EditTextUtils.getTextString
import com.android.wallet.utils.ExtensionUtils.EditTextUtils.onDone

class ResetPasswordActivity : BaseActivity<ActivityResetPasswordBinding>() {

    private lateinit var viewModel: ResetPasswordViewModel
    private lateinit var repository: ResetPasswordRepository

    override fun getBinding(inflater: LayoutInflater): ActivityResetPasswordBinding {
        return ActivityResetPasswordBinding.inflate(inflater)
    }

    override fun onDoneKeyboardClicked(binding: ActivityResetPasswordBinding) {
        binding.apply {
            editTextConfirmPassword.onDone {
                buttonVerify.performClick()
            }
        }
    }

    override fun initViews(binding: ActivityResetPasswordBinding) {
        repository = ResetPasswordRepository(this)
        viewModel = ViewModelProvider(
            this, ResetPasswordViewModelFactory(repository)
        )[ResetPasswordViewModel::class.java]
    }

    override fun setupToolbar(binding: ActivityResetPasswordBinding) {}

    override fun onClickListeners(binding: ActivityResetPasswordBinding) {
        with(binding) {
            buttonVerify.setOnClickListener {
                if (editTextOldPassword.getTextString() == "") {
                    showMessage(
                        getString(R.string.please_enter_your_old_password), MESSAGE_COLOR_ERROR
                    )
                    return@setOnClickListener
                }
                if (editTextNewPassword.getTextString().isEmpty()) {
                    showMessage(
                        getString(R.string.please_enter_your_new_password),
                        MESSAGE_COLOR_ERROR
                    )
                    return@setOnClickListener
                }
                if (editTextNewPassword.getTextString().length < 4 || editTextNewPassword.getTextString().length > 8) {
                    showMessage(
                        getString(R.string.password_length_should_not_be_less_than_4_and_greater_than_8),
                        MESSAGE_COLOR_ERROR
                    )
                    return@setOnClickListener
                }
                if (editTextConfirmPassword.getTextString() != editTextNewPassword.getTextString()) {
                    showMessage(
                        getString(R.string.confirm_password_is_not_same_as_new_password),
                        MESSAGE_COLOR_ERROR
                    )
                    return@setOnClickListener
                }
                resetPasswordApi()
            }
        }
    }

    private fun resetPasswordApi() {
        binding.apply {
            viewModel.resetPasswordApi<LoginResponse?>(
                ResetPasswordRequest(
                    oldPassword = editTextOldPassword.getTextString(),
                    newPassword = editTextNewPassword.getTextString()
                )
            )
        }
    }

    override fun liveDataObservers(binding: ActivityResetPasswordBinding) {
        viewModel.resetPasswordNewPasswordResponse.observe(this) {
            when (it.localStatus) {
                Status.SUCCESS -> {
                    doLogout(goToDashboard = true)
                }

                else -> {}
            }
        }
    }
}