package com.phuongtai.myconverter.pages.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.phuongtai.myconverter.SPLASH_UI_DURATION
import com.phuongtai.myconverter.databinding.ActivitySplashBinding
import com.phuongtai.myconverter.pages.base.BaseActivity
import com.phuongtai.myconverter.pages.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun observeViewModel() {
    }

    override fun initViewBinding() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToHome()
    }

    private fun navigateToHome() {
        Handler().postDelayed(
            {
                val homeIntent = Intent(this, HomeActivity::class.java)
                // Start the HomeActivity without ability to go back to the SplashActivity
                startActivity(homeIntent)
                finish()
            },
            SPLASH_UI_DURATION
        )
    }
}