package com.reddit.presentation.common.error

import com.reddit.domain.exception.APIException
import timber.log.Timber
import javax.inject.Inject

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 16:27
 */
class DefaultErrorHandler
@Inject constructor() : ErrorHandler {
    override fun handleError(throwable: Throwable) {
        handleError(throwable, null)
    }

    override fun handleError(throwable: Throwable, errorView: ((message: String) -> Unit)?) {
        Timber.e(throwable)
        errorView?.let {
            val message: String? = if (throwable is APIException) {
                throwable.message
            } else {
                "Server Error"
            }
            if (message != null) errorView.invoke(message)
        }
    }
}