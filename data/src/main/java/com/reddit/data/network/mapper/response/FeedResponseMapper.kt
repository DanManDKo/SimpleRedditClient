package com.reddit.data.network.mapper.response

import android.text.format.DateUtils
import com.reddit.data.network.dto.FeedDto
import com.reddit.data.network.mapper.Mapper
import com.reddit.domain.model.Feed
import javax.inject.Inject

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 21:51
 */
class FeedResponseMapper
@Inject
constructor() : Mapper<FeedDto.Children, Feed> {
    override fun map(value: FeedDto.Children): Feed {
        return Feed(
            value.data.id,
            value.data.title,
            value.data.score,
            value.data.numComments,
            getImage(value),
            formatRelativeTime(value),
            value.data.subredditNamePrefixed,
            value.data.author,
            value.data.after,
            value.data.permalink
        )
    }

    private fun getImage(children: FeedDto.Children): String? {
        return if (children.data.preview != null && children.data.preview.images.isNotEmpty()) {
            children.data.preview.images[0].source.url
        } else {
            return null
        }
    }

    private fun formatRelativeTime(children: FeedDto.Children): String {
        val timeStamp = children.data.createdUtc * 1000
        return DateUtils.getRelativeTimeSpanString(
            timeStamp,
            System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_WEEKDAY
        ).toString()
    }
}