package com.reddit.data.feature.feed

import com.reddit.data.network.mapper.response.FeedResponseMapper
import com.reddit.data.network.service.FeedService
import com.reddit.data.util.Mappers
import com.reddit.domain.model.Feed
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 21:04
 */
class FeedNetworkStorage
@Inject
constructor(
    private val feedService: FeedService,
    private val feedResponseMapper: FeedResponseMapper
) {
    fun getFeed(offset: Int, limit: Int, period: String, after: String?): Single<List<Feed>> {
        return feedService.getTopList(offset, limit, period, after)
            .map { dto ->
                dto.data.children.forEach { child ->
                    child.data.after = dto.data.after
                }
                dto
            }
            .map { Mappers.mapCollection(it.data.children, feedResponseMapper) }
    }
}