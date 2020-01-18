package com.reddit.data.network.mapper

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 21:17
 */
interface Mapper<E, R> {
    fun map(value: E): R
}