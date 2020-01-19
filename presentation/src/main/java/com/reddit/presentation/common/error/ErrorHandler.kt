package com.reddit.presentation.common.error

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 16:26
 */
interface ErrorHandler {
    fun handleError(throwable: Throwable)

    fun handleError(throwable: Throwable, errorView: ((message: String) -> Unit)?)
}