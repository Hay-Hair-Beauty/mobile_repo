package com.capstone.hay.view.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.capstone.hay.databinding.ActivitySplashBinding
import com.capstone.hay.view.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            navigateToMain()
        }, 3000L)

        setupView()
        setupAnimation()
    }


    private fun setupAnimation() {
        val firstAnimation = ObjectAnimator.ofFloat(binding.splashScreenImage, View.ALPHA, 1f).setDuration(1500)
        val endAnimation = ObjectAnimator.ofFloat(binding.splashScreenImage, View.ALPHA, 0f).setDuration(1500)
        AnimatorSet().apply {
            playSequentially(firstAnimation, endAnimation)
            start()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}