package com.capstone.hay.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.capstone.hay.R
import com.capstone.hay.databinding.ActivityLoginBinding
import com.capstone.hay.view.ViewModelFactory
import com.capstone.hay.view.main.MainActivity
import com.capstone.hay.view.register.RegisterActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var loadingDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupObserve()
        redirectToLogin()
        setupLogin()
    }

    private fun setupObserve() {
        viewModel.loginResult.observe(this) { result ->
            result.onSuccess { response ->
                MaterialAlertDialogBuilder(this@LoginActivity)
                    .setTitle(resources.getString(R.string.title_dialog))
                    .setMessage(R.string.supporting_text_dialog)
                    .setPositiveButton(resources.getString(R.string.accept_dialog)) { dialog, which ->
                        navigateToMainActivity()
                    }
                    .show()

            }.onFailure { exception ->
                showSnackbar(exception.message ?: "Unknown error")
            }
        }

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
    }

    private fun setupView() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        supportActionBar?.hide()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun redirectToLogin() {
        binding.tvRedirectToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupLogin() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            viewModel.login(email, password)
        }
    }

    private fun showLoadingDialog(){
        if (loadingDialog == null) {
            val dialogView = layoutInflater.inflate(R.layout.dialog_loading, null)
            loadingDialog = MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create()
        }
        loadingDialog?.show()
    }

    private fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}