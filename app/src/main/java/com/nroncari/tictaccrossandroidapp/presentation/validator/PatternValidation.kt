package com.nroncari.tictaccrossandroidapp.presentation.validator

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class PatternValidation(private val textInputField: TextInputLayout) :
    Validator {
    private val field: EditText = this.textInputField.editText!!

    private fun validate(): Boolean {
        val text: String = field.text.toString()
        if (text.isEmpty()) {
            textInputField.error = REQUIRED_FIELD
            return false
        }
        return true
    }

    override fun isValid(): Boolean {
        if (!validate()) return false
        removeError()
        return true
    }

    private fun removeError() {
        textInputField.error = null
        textInputField.isErrorEnabled = false
    }

    companion object {
        private const val REQUIRED_FIELD = "Required field"
    }

}