package com.reddit.domain.model

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 20:51
 */
data class Feed(
    val id: String,
    val title: String,
    val score: Int,
    val commentsCount: Int,
    val image: Image?,
    val date: String,
    val subredditNamePrefixed: String,
    val authorName: String,
    val after: String?,
    var permalink: String
)
