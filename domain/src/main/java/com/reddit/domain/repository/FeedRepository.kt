package com.reddit.domain.repository

import com.reddit.domain.model.Feed
import com.reddit.domain.model.PageBundle
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 17:03
 */
interface FeedRepository : Repository {
    fun refresh(): Completable
    fun observeFeed(): Observable<PageBundle<Feed>>
    fun fetchNext(): Completable
}