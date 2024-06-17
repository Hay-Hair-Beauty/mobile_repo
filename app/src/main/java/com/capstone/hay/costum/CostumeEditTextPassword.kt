package com.capstone.hay.costum

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.capstone.hay.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CostumeEditTextPassword(context: Context, attrs: AttributeSet) : TextInputEditText(context, attrs) {
    private var textInputLayout: TextInputLayout? = null

    fun setTextInputLayout(textInputLayout: TextInputLayout) {
        this.textInputLayout = textInputLayout
    }

    init {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) < 8) {
                    textInputLayout?.error = context.getString(R.string.error_message_password)
                } else {
                    textInputLayout?.error = null
                }
            }
        })
    }
}