package com.reddit.presentation.common.extensions

import com.reddit.presentation.common.databinding.SingleLiveEvent
import com.reddit.presentation.common.error.ErrorHandler
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-19
 * Time: 15:08
 */

/**
 * @see ErrorHandler
 * @param errorHandler error handler to handle error
 * @param errorMessage event to handle error
 */
fun <T> Observable<T>.defaultDoOnError(
    errorHandler: ErrorHandler,
    errorMessage: SingleLiveEvent<String>
): Observable<T> {
    return this.doOnError { error ->
        errorHandler.handleError(error) { message ->
            errorMessage.value = message
        }
    }
}

fun <T> Single<T>.defaultDoOnError(
    errorHandler: ErrorHandler,
    errorMessage: SingleLiveEvent<String>
): Single<T> {
    return this.doOnError { error ->
        errorHandler.handleError(error) { message ->
            errorMessage.value = message
        }
    }
}

fun Completable.defaultDoOnError(
    errorHandler: ErrorHandler,
    errorMessage: SingleLiveEvent<String>
): Completable {
    return this.doOnError { error ->
        errorHandler.handleError(error) { message ->
            errorMessage.value = message
        }
    }
}

fun <T> Single<T>.defaultSubscribe(errorHandler: ErrorHandler,
                                   error: SingleLiveEvent<String>,
                                   onSuccess: ((T) -> Unit)? = null): Disposable {
    val function = if (onSuccess != null) {
        onSuccess
    } else {
        {}
    }
    return this.subscribe(function, { throwable ->
        errorHandler.handleError(throwable)
        { error.value = it }
    })
}


fun <T> Single<T>.defaultSubscribe(errorHandler: ErrorHandler,
                                   onSuccess: ((t: T) -> Unit)? = null): Disposable {
    val function = if (onSuccess != null) {
        onSuccess
    } else {
        {}
    }
    return this.subscribe(function, { throwable ->
        errorHandler.handleError(throwable)
    })
}

fun <T> Observable<T>.defaultSubscribe(errorHandler: ErrorHandler,
                                       error: SingleLiveEvent<String>,
                                       onSuccess: ((t: T) -> Unit)? = null): Disposable {
    val function = if (onSuccess != null) {
        onSuccess
    } else {
        {}
    }
    return this.subscribe(function, { throwable ->
        errorHandler.handleError(throwable)
        { error.value = it }
    })
}

fun <T> Observable<T>.defaultSubscribe(errorHandler: ErrorHandler,
                                       onSuccess: ((t: T) -> Unit)? = null): Disposable {
    val function = if (onSuccess != null) {
        onSuccess
    } else {
        {}
    }
    return this.subscribe(function, { throwable ->
        errorHandler.handleError(throwable)
    })
}

fun Completable.defaultSubscribe(errorHandler: ErrorHandler,
                                 error: SingleLiveEvent<String>,
                                 onSuccess: (() -> Unit)? = null): Disposable {
    val function = if (onSuccess != null) {
        onSuccess
    } else {
        {}
    }
    return this.subscribe(function, { throwable ->
        errorHandler.handleError(throwable)
        { error.value = it }
    })
}

fun Completable.defaultSubscribe(errorHandler: ErrorHandler,
                                 onSuccess: (() -> Unit)? = null): Disposable {
    val function = if (onSuccess != null) {
        onSuccess
    } else {
        {}
    }
    return this.subscribe(function, { throwable ->
        errorHandler.handleError(throwable)
    })
}
