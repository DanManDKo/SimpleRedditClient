package com.reddit.domain.type

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 21:14
 */
enum class ContentState {
    CONTENT, EMPTY, LOADING, ERROR;

    val isContent: Boolean get() = this == CONTENT

    val isLoading: Boolean get() = this == LOADING

    val isEmpty: Boolean get() = this == EMPTY

    val isError: Boolean get() = this == ERROR

}