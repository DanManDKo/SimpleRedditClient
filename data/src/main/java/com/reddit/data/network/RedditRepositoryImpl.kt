package com.reddit.data.network

import com.reddit.data.feature.feed.FeedNetworkStorage
import com.reddit.domain.model.Feed
import com.reddit.domain.model.PageBundle
import com.reddit.domain.repository.FeedRepository
import io.reactivex.Completable
import io.reactivex.Observable
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
constructor(private val feedNetworkStorage: FeedNetworkStorage) : FeedRepository {

    override fun refresh(): Completable {
        return feedNetworkStorage.refresh()
    }

    override fun observeFeed(): Observable<PageBundle<Feed>> {
        return feedNetworkStorage.getFeed()
    }

    override fun fetchNext(): Completable {
        return feedNetworkStorage.fetchNext()
    }
}