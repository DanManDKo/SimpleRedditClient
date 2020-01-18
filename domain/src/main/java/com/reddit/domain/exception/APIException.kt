package com.reddit.domain.exception

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 16:29
 */
open class APIException(val code: Int, message: String?) : Exception(message) {

    override fun toString(): String {
        return message ?: ""
    }
}