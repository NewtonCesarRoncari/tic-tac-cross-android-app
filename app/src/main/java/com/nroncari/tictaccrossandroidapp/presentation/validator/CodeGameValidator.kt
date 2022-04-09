package com.nroncari.tictaccrossandroidapp.presentation.validator

import com.google.android.material.textfield.TextInputLayout

class CodeGameValidator(private val textInput: TextInputLayout): Validator {
    private val fieldCode = this.textInput.editText!!
    private val patternValidation = PatternValidation(this.textInput)

    private fun valid(code: String): Boolean {
        if (isCodeValid(code))
            return true
        textInput.error = "The code must be 8 characters"
        return false
    }

    override fun isValid(): Boolean {
        if (!patternValidation.isValid()) return false
        val code = fieldCode.text.toString()
        return valid(code)
    }

    private fun isCodeValid(code: String) = (code.length == 8)

}