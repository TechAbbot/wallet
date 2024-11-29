package com.android.wallet.ui.confirmpassword

import android.app.Activity
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.android.wallet.R
import com.android.wallet.databinding.ActivityConfirmPasswordBinding
import com.android.wallet.model.login.LoginResponse
import com.android.wallet.network.Status
import com.android.wallet.repository.confirmpassword.ConfirmPasswordRepository
import com.android.wallet.ui.base.BaseActivity
import com.android.wallet.model.confirmpassword.request.ConfirmPasswordRequest
import com.android.wallet.ui.confirmpassword.viewmodel.ConfirmPasswordViewModel
import com.android.wallet.ui.confirmpassword.viewmodel.ConfirmPasswordViewModelFactory
import com.android.wallet.utils.AppConstants
import com.android.wallet.utils.AppConstants.MessageBackgroundColor.MESSAGE_COLOR_ERROR
import com.android.wallet.utils.ExtensionUtils.EditTextUtils.getTextString
import com.android.wallet.utils.ExtensionUtils.EditTextUtils.onDone

class ConfirmPasswordActivity : BaseActivity<ActivityConfirmPasswordBinding>() {

    private lateinit var viewModel: ConfirmPasswordViewModel
    private lateinit var repository: ConfirmPasswordRepository

    override fun getBinding(inflater: LayoutInflater): ActivityConfirmPasswordBinding {
        return ActivityConfirmPasswordBinding.inflate(inflater)
    }

    override fun onDoneKeyboardClicked(binding: ActivityConfirmPasswordBinding) {
        binding.apply {
            editTextConfirmPassword.onDone {
                buttonVerify.performClick()
            }
        }
    }

    override fun initViews(binding: ActivityConfirmPasswordBinding) {
        repository = ConfirmPasswordRepository(this)
        viewModel = ViewModelProvider(
            this, ConfirmPasswordViewModelFactory(repository)
        )[ConfirmPasswordViewModel::class.java]
    }

    override fun setupToolbar(binding: ActivityConfirmPasswordBinding) {}

    override fun onClickListeners(binding: ActivityConfirmPasswordBinding) {
        with(binding) {
            buttonVerify.setOnClickListener {
                if (editTextPassword.getTextString() == "") {
                    showMessage(getString(R.string.please_enter_password), MESSAGE_COLOR_ERROR)
                    return@setOnClickListener
                }
                if (editTextConfirmPassword.getTextString() != editTextPassword.getTextString()) {
                    showMessage(
                        getString(R.string.confirm_password_is_not_same_as_password),
                        MESSAGE_COLOR_ERROR
                    )
                    return@setOnClickListener
                }
                verifyPasswordApi()
            }
        }
    }

    override fun liveDataObservers(binding: ActivityConfirmPasswordBinding) {
        viewModel.confirmPasswordNewPasswordResponse.observe(this) {
            when (it.localStatus) {
                Status.SUCCESS -> {
                    sharedPref.putString(AppConstants.LOGIN_PASSCODE, "")
                    setResult(Activity.RESULT_OK)
                    finish()
                }

                else -> {

                }
            }
        }
    }


    private fun verifyPasswordApi() {
        viewModel.verifyPasswordApi<LoginResponse?>(
            ConfirmPasswordRequest(
                sharedPref.getString(AppConstants.USER_ID),
                binding.editTextPassword.getTextString(),
            )
        )
    }
}