package com.reddit.presentation.feature.feed

import androidx.lifecycle.MutableLiveData
import com.reddit.domain.interactors.FeedInteractor
import com.reddit.domain.model.Feed
import com.reddit.domain.type.ContentState
import com.reddit.domain.type.FeedPeriod
import com.reddit.presentation.common.BaseViewModel
import com.reddit.presentation.common.databinding.SingleLiveEvent
import com.reddit.presentation.common.error.DefaultErrorHandler
import com.reddit.presentation.common.extensions.defaultSubscribe
import com.reddit.presentation.common.extensions.observeForever
import com.reddit.presentation.utils.RxDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 19:43
 */
class FeedViewModel
@Inject
constructor(
    private val feedInteractor: FeedInteractor,
    private val errorHandler: DefaultErrorHandler
) : BaseViewModel() {

    val feedType = MutableLiveData<FeedType>()
    var hasNext = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
    var contentState = MutableLiveData<ContentState>()
    var content = MutableLiveData<List<Feed>>()

    var feedClickedEvent = SingleLiveEvent<Feed>()

    init {
        observeForever(feedType) {
            observeFeedByType(it)
        }
    }

    fun loadMore() {
        RxDisposable.manage(
            this, "loadMore",
            feedInteractor.fetchNext(FeedPeriod.ALL)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.value = true }
                .doOnTerminate { loading.value = false }
                .defaultSubscribe(errorHandler, errorMessage)
        )
    }

    fun onFeedClicked(feed: Feed) {
        feedClickedEvent.value = feed
    }

    fun onFavoriteBtnClicked(feed: Feed) {
        if (feed.isFavorite) {
            removeFromFavorite(feed)
        } else {
            addToFavorite(feed)
        }
    }

    private fun addToFavorite(feed: Feed) {
        RxDisposable.manage(
            this, "addToFavorite",
            feedInteractor.addToFavorite(feed)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { uploading.value = true }
                .doOnTerminate { uploading.value = false }
                .defaultSubscribe(errorHandler, errorMessage)
        )
    }

    private fun removeFromFavorite(feed: Feed) {
        RxDisposable.manage(
            this, "removeFromF",
            feedInteractor.removeFromFavorite(feed)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { uploading.value = true }
                .doOnTerminate { uploading.value = false }
                .defaultSubscribe(errorHandler, errorMessage)
        )
    }

    private fun observeFeedByType(feedType: FeedType) {
        if (feedType == FeedType.MAIN) {
            observeFeed()
        } else {
            observeFeedFavorites()
        }
    }

    private fun observeFeed() {
        RxDisposable.manage(this, "observeFeed",
            feedInteractor.observeFeed(FeedPeriod.ALL)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { contentState.setValue(ContentState.LOADING) }
                .doOnError { contentState.setValue(ContentState.ERROR) }
                .doOnNext {
                    if (it.data.isEmpty()) contentState.value = ContentState.EMPTY
                    else contentState.value = ContentState.CONTENT
                }
                .defaultSubscribe(errorHandler, errorMessage) {
                    content.value = it.data
                    hasNext.value = it.hasNext
                })
    }

    private fun observeFeedFavorites() {
        RxDisposable.manage(this, "observeFavorites",
            feedInteractor.observeFavorites()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { contentState.setValue(ContentState.LOADING) }
                .doOnError { contentState.setValue(ContentState.ERROR) }
                .defaultSubscribe(errorHandler, errorMessage) {
                    content.value = it
                    hasNext.value = false
                })
    }
}