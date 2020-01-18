package com.reddit.domain.interactors

import com.reddit.domain.model.Feed
import com.reddit.domain.model.PageBundle
import com.reddit.domain.repository.FeedRepository
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

    fun refresh(): Completable {
        return feedRepository.refresh()
    }

    fun observeFeed(): Observable<PageBundle<Feed>>{
        return feedRepository.observeFeed()
    }

    fun fetchNext(): Completable{
        return feedRepository.fetchNext()
    }

}