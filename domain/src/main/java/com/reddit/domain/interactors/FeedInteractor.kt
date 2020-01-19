package com.reddit.domain.interactors

import com.reddit.domain.model.Feed
import com.reddit.domain.model.PageBundle
import com.reddit.domain.repository.FeedRepository
import com.reddit.domain.type.FeedPeriod
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 20:41
 */
class FeedInteractor
@Inject
constructor(private val feedRepository: FeedRepository) {

    fun refresh(period: FeedPeriod): Completable {
        return feedRepository.refresh(period)
    }

    fun observeFeed(period: FeedPeriod): Observable<PageBundle<Feed>> {
        return feedRepository.observeFeed(period)
    }

    fun fetchNext(period: FeedPeriod): Completable {
        return feedRepository.fetchNext(period)
    }

    fun addToFavorite(feed: Feed): Completable {
        return feedRepository.storeFavorite(feed)
    }

    fun observeFavorites(): Observable<List<Feed>> {
        return feedRepository.observeFavorites()
    }

}