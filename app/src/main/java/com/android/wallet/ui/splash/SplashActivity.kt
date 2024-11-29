package com.android.wallet.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.android.wallet.databinding.ActivitySplashBinding
import com.android.wallet.ui.base.BaseActivity
import com.android.wallet.ui.login.LoginActivity
import com.android.wallet.ui.passcode.PasscodeActivity
import com.android.wallet.ui.welcome.WelcomeActivity
import com.android.wallet.utils.AppConstants

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun getBinding(inflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(inflater)
    }

    override fun onDoneKeyboardClicked(binding: ActivitySplashBinding) {

    }

    override fun initViews(binding: ActivitySplashBinding) {
        installSplashScreen()
        Handler(Looper.getMainLooper()).postDelayed({
            loginLogic()
        }, 2000)
    }

    override fun setupToolbar(binding: ActivitySplashBinding) {}

    override fun onClickListeners(binding: ActivitySplashBinding) {

    }

    override fun liveDataObservers(binding: ActivitySplashBinding) {

    }

    private fun loginLogic() {
        val i = if (!sharedPref.getBoolean(AppConstants.IS_WELCOME)) {
            Intent(
                this@SplashActivity, WelcomeActivity::class.java
            ).setAction("your.custom.action")
        } else if (sharedPref.getBoolean(AppConstants.IS_LOGIN)) {
            Intent(this@SplashActivity, PasscodeActivity::class.java).putExtra(
                AppConstants.MPinActionStatus.STATUS_KEY, AppConstants.MPinActionStatus.SPLASH
            ).setAction("your.custom.action")
        } else {
            Intent(
                this@SplashActivity, LoginActivity::class.java
            ).setAction("your.custom.action")
        }
        startActivity(i)
    }

}