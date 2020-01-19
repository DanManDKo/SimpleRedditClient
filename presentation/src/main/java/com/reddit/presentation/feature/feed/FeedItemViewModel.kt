package com.reddit.presentation.feature.feed

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.reddit.domain.model.Feed
import com.reddit.presentation.R

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-19
 * Time: 14:44
 */
class FeedItemViewModel(private val context: Context) {
    var feed: Feed? = null
    var author: String? = null
    var title: String? = null
    var contentImageUri: String? = null
    var rating: String? = null
    var commentsCount: String? = null
    var data: String? = null
    var imageBtnFavorite: Drawable? = null

    private val imageFavorite by lazy {
        ContextCompat.getDrawable(context, R.drawable.ic_heart_fill)!!
    }

    private val imageNotFavorite: Drawable by lazy {
        ContextCompat.getDrawable(context, R.drawable.ic_heart)!!
    }

    fun bind(item: Feed) {
        feed = item
        author = item.authorName
        title = item.title
        contentImageUri = item.image?.url
        rating = item.score.toString()
        commentsCount = item.commentsCount.toString()
        data = item.date
        imageBtnFavorite = imageNotFavorite
    }

}