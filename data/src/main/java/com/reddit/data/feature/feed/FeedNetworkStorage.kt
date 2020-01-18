package com.reddit.data.feature.feed

import com.reddit.data.network.service.FeedService
import com.reddit.domain.model.Feed
import com.reddit.domain.model.PageBundle
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 21:04
 */
class FeedNetworkStorage
@Inject
constructor(private val feedService: FeedService) {
    fun refresh(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getFeed(): Observable<PageBundle<Feed>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun fetchNext() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}