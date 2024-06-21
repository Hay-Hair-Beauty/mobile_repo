package com.capstone.hay.view.verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.capstone.hay.R
import com.capstone.hay.databinding.ActivityVerificationBinding
import com.capstone.hay.view.ViewModelFactory
import com.capstone.hay.view.login.LoginActivity
import com.capstone.hay.view.register.RegisterActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
    private val viewModel by viewModels<VerificationViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var loadingDialog: AlertDialog? = null
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra(EXTRA_EMAIL)
        if (email != null) {
            setupVerification(email)
        } else {
            redirectToRegister()
        }

        binding.resendNowCodeVerification.setOnClickListener {
            if (email != null) {
                viewModel.resendVerificationCode(email)
            }
        }

        binding.codeVerification1.requestFocus()
        setupObserve()
        setupView()
        setupTime()
    }

    private fun setupTime() {
        val totalTimeInMillis: Long = 5 * 60 * 1000
        startTimer(totalTimeInMillis)
    }

    private fun startTimer(totalTimeInMillis: Long) {
        countDownTimer = object : CountDownTimer(totalTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(minutes)

                binding.infoCountTimerExpiredCode.text = getString(R.string.info_count_timer, minutes, seconds)
            }

            override fun onFinish() {
                binding.infoCountTimerExpiredCode.text = getString(R.string.info_count_timer, 0, 0)
            }
        }

        countDownTimer.start()
    }

    private fun resetTimer() {
        countDownTimer.cancel()
        startTimer(5 * 60 * 1000)
    }

    private fun setupVerification(email: String) {
        binding.btnConfirm.setOnClickListener {
            val code = binding.codeVerification1.text.toString().trim() +
                    binding.codeVerification2.text.toString().trim() +
                    binding.codeVerification3.text.toString().trim() +
                    binding.codeVerification4.text.toString().trim() +
                    binding.codeVerification5.text.toString().trim() +
                    binding.codeVerification6.text.toString().trim()

            viewModel.verificationAccount(email, code)
        }
    }

    private fun setupObserve() {
        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                showLoadingDialog()
            } else {
                dismissLoadingDialog()
            }
        }

        viewModel.snackbarText.observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                showSnackbar(message)
            }
        }

        viewModel.verifyResult.observe(this) { result ->
            result.onSuccess {
                navigateToLogin()
            }
        }

        viewModel.resendVerifyResult.observe(this) { result ->
            result.onSuccess {
                resetTimer()
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun showLoadingDialog() {
        if (loadingDialog == null) {
            val dialogView = layoutInflater.inflate(R.layout.dialog_loading, null)
            loadingDialog = MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create()
        }
        loadingDialog?.show()
    }

    private fun redirectToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun setupView() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        supportActionBar?.hide()
    }

    companion object {
        const val EXTRA_EMAIL = "extra_email"
    }
}
