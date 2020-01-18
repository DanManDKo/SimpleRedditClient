package com.reddit.domain.repository

import com.reddit.domain.model.Feed
import com.reddit.domain.model.PageBundle
import com.reddit.domain.type.FeedPeriod
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 17:03
 */
interface FeedRepository : Repository {
    fun refresh(period: FeedPeriod): Completable
    fun observeFeed(period: FeedPeriod): Observable<PageBundle<Feed>>
    fun fetchNext(period: FeedPeriod): Completable
}