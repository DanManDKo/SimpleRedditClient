package com.reddit.data.network

import com.reddit.data.common.cache.CachePolicy
import com.reddit.data.common.storage.MemoryPagedListStorage
import com.reddit.data.feature.feed.FeedDatabaseStorage
import com.reddit.data.feature.feed.FeedNetworkStorage
import com.reddit.data.network.mapper.request.PeriodRequestMapper
import com.reddit.domain.model.Feed
import com.reddit.domain.model.PageBundle
import com.reddit.domain.repository.FeedRepository
import com.reddit.domain.type.FeedPeriod
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 17:26
 */
@Singleton
class RedditRepositoryImpl
@Inject
constructor(
    private val feedNetworkStorage: FeedNetworkStorage,
    private val periodRequestMapper: PeriodRequestMapper,
    private val feedDatabaseStorage: FeedDatabaseStorage
) : FeedRepository {

    private val memoryStorage = MemoryPagedListStorage
        .Builder<FeedPeriod, Feed> { params ->
            feedNetworkStorage.getFeed(
                params.page,
                params.limit,
                periodRequestMapper.map(params.query),
                params.entity?.after
            )
                .map { MemoryPagedListStorage.FetchResult(it, it.isNotEmpty()) }
        }
        .cachePolicy(CachePolicy.create(1, TimeUnit.MINUTES))
        .capacity(1)
        .build()


    override fun refresh(period: FeedPeriod): Completable {
        return memoryStorage.refresh(period)
    }

    override fun observeFeed(period: FeedPeriod): Observable<PageBundle<Feed>> {
        return memoryStorage[period]
    }

    override fun fetchNext(period: FeedPeriod): Completable {
        return memoryStorage.fetchNext(period)
    }

    override fun observeFavorites(): Observable<List<Feed>> {
        return feedDatabaseStorage.observeFeed()
    }

    override fun storeFavorite(feed: Feed): Completable {
        return feedDatabaseStorage.storeFeed(feed)
    }

    override fun removeFavorite(feed: Feed): Completable {
        return feedDatabaseStorage.removeFeed(feed)
    }
}