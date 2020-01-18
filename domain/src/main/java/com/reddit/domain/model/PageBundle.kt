package com.reddit.domain.model

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 20:50
 */
data class PageBundle<T>(
    val data: List<T>,
    val hasNext: Boolean,
    val maxCount: Int = 0
)