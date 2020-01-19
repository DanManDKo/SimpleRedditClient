package com.reddit.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 20:51
 */
@Entity
data class Feed(
    @PrimaryKey
    val id: String,
    val title: String,
    val score: Int,
    val commentsCount: Int,
    val imageUri: String?,
    val date: String,
    val subredditNamePrefixed: String,
    val authorName: String,
    val after: String?,
    var permalink: String,
    var isFavorite: Boolean
)
