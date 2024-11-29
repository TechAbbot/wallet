package com.android.wallet.ui.welcome

import android.content.Intent
import android.view.LayoutInflater
import com.android.wallet.databinding.ActivityWelcomeBinding
import com.android.wallet.ui.base.BaseActivity
import com.android.wallet.ui.login.LoginActivity
import com.android.wallet.utils.AppConstants

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {

    override fun getBinding(inflater: LayoutInflater): ActivityWelcomeBinding {
        return ActivityWelcomeBinding.inflate(inflater)
    }

    override fun onDoneKeyboardClicked(binding: ActivityWelcomeBinding) {
        
    }

    override fun initViews(binding: ActivityWelcomeBinding) {

    }

    override fun setupToolbar(binding: ActivityWelcomeBinding) {}

    override fun onClickListeners(binding: ActivityWelcomeBinding) {
        binding.apply {
            btWelcomeGetStarted.setOnClickListener {
                sharedPref.putBoolean(AppConstants.IS_WELCOME, true)
                startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java).setAction("your.custom.action"))
                finish()
            }
        }
    }

    override fun liveDataObservers(binding: ActivityWelcomeBinding) {

    }
}