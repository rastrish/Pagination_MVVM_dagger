package com.zup.cash.utils.common

import com.zup.cash.R
import java.util.regex.Pattern

object Validator {

    private val EMAIL_ADDRESS = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    private const val MIN_PASSWORD_LENGTH = 6

    fun validateLoginFields(phoneNumber: String?): List<Validation> =
        ArrayList<Validation>().apply {
            when {

            }
            when {

            }
        }
}

data class Validation(val field: Field, val resource: Resource<Int>) {

    enum class Field {
        EMAIL,
        PASSWORD
    }
}
