package com.example.truecallerassignmentapplication.ui.exception

import android.content.Context
import com.example.truecallerassignmentapplication.R
import com.example.truecallerassignmentapplication.domain.NotFoundException
import com.example.truecallerassignmentapplication.domain.UnauthorizedException
import com.example.truecallerassignmentapplication.infrastructure.InfrastructureException
import com.example.truecallerassignmentapplication.infrastructure.NetworkException


/**
 * Factory used to create error messages from an Exception as a condition.
 */
class ErrorMessageFactory {
    companion object {
        fun create(context: Context, t: Throwable?=null): String {
            var message = context.getString(R.string.error_happen_try_again)

            when (t) {
                is UnauthorizedException -> {
                    message = context.getString(R.string.not_authorized_error_happen_try_again)
                }
                is NotFoundException -> message = context.getString(R.string.not_found_error_happen_try_again)

                is NetworkException -> message = t.cause?.message.toString()

                is InfrastructureException -> message = t.cause?.message.toString()

            }
            return message
        }
    }
}
