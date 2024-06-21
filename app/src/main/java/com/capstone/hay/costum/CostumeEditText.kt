package com.capstone.hay.costum

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.capstone.hay.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CostumeEditText(context: Context, attrs: AttributeSet) : TextInputEditText(context, attrs) {
    private var textInputLayout: TextInputLayout? = null
    private var password: String = ""

    fun setTextInputLayout(textInputLayout: TextInputLayout) {
        this.textInputLayout = textInputLayout
    }

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()

                if (textInputLayout?.hint?.toString() == context.getString(R.string.emailHint)) {
                    validateEmail(text)
                } else if (textInputLayout?.hint?.toString() == context.getString(R.string.passwordHint)) {
                    password = text
                    validatePassword(text)
                } else if (textInputLayout?.hint?.toString() == context.getString(R.string.nameHint)) {
                    validateName(text)
                } else if (textInputLayout?.hint?.toString() == context.getString(R.string.phoneNumberHint)) {
                    validatePhoneNumber(text)
                } else if (textInputLayout?.hint?.toString() == context.getString(R.string.confirmPasswordHint)) {
                    validateConfirmPassword(text)
                }
            }
        })
    }

    private fun validateConfirmPassword(confirmPassword: String) {
        when {
            isEmptyPassword(confirmPassword) -> {
                textInputLayout?.error = context.getString(R.string.validation_empty_confirm_password)
            }
            checkLengthPassword(confirmPassword) -> {
                textInputLayout?.error = context.getString(R.string.validation_must_have_least_confirm_password)
            }
            !containsUppercase(confirmPassword) -> {
                textInputLayout?.error = context.getString(R.string.validation_contain_uppercase_confirm_password)
            }
            !confirmPasswordMatch(confirmPassword) -> {
                textInputLayout?.error = context.getString(R.string.validation_contain_uppercase_confirm_password)
            }
            else -> {
                textInputLayout?.error = null
            }
        }
    }

    private fun validatePhoneNumber(phoneNumber: String) {
        if (phoneNumber.isEmpty()) {
            textInputLayout?.error = context.getString(R.string.validation_empty_phone_number)
        } else if (!isPhoneNumberValid(phoneNumber)) {
            textInputLayout?.error = context.getString(R.string.validation_valid_phone_number)
        } else {
            textInputLayout?.error = null
        }
    }

    private fun validateName(name: String) {
        when {
            name.isEmpty() -> {
                textInputLayout?.error = context.getString(R.string.validation_empty_name)
            }
            name.length > 20 -> {
                textInputLayout?.error = context.getString(R.string.validation_no_more_length_name)
            }
            else -> {
                textInputLayout?.error = null
            }
        }
    }

    private fun validateEmail(email: String) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayout?.error = context.getString(R.string.error_message_email_input)
        } else {
            textInputLayout?.error = null
        }
    }

    private fun validatePassword(password: String) {
        when {
            isEmptyPassword(password) -> {
                textInputLayout?.error = context.getString(R.string.validation_empty_password)
            }
            checkLengthPassword(password) -> {
                textInputLayout?.error = context.getString(R.string.error_message_password)
            }
            !containsUppercase(password) -> {
                textInputLayout?.error = context.getString(R.string.validation_contain_uppercase_password)
            }
            else -> {
                textInputLayout?.error = null
            }
        }
    }

    private fun containsUppercase(text: String): Boolean {
        for (char in text) {
            if (char.isUpperCase()) {
                return true
            }
        }
        return false
    }

    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        val phonePattern = Regex("^[+]?[0-9]{10,13}\$")
        return phonePattern.matches(phoneNumber)
    }

    private fun isEmptyPassword(password: String): Boolean {
        return password.isEmpty()
    }

    private fun checkLengthPassword(password: String): Boolean {
        return password.length < 8
    }

    private fun confirmPasswordMatch(confirmPassword: String) : Boolean {
        return password != confirmPassword
    }

}
