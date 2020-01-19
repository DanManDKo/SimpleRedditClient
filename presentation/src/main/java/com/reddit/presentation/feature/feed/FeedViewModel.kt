package com.reddit.presentation.feature.feed

import androidx.lifecycle.MutableLiveData
import com.reddit.domain.interactors.FeedInteractor
import com.reddit.presentation.common.BaseViewModel
import com.reddit.presentation.common.databinding.observeForever
import javax.inject.Inject

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 19:43
 */
class FeedViewModel
@Inject
constructor(private val feedInteractor: FeedInteractor) : BaseViewModel() {

    val feedType = MutableLiveData<FeedType>()

    init {
        observeForever(feedType) {
            observeFeedByType(it)
        }
    }

    private fun observeFeedByType(feedType: FeedType) {
        if (feedType == FeedType.MAIN) {
            observeFeed()
        } else {
            observeFeedFavorites()
        }
    }

    private fun observeFeed() {

    }

    private fun observeFeedFavorites() {

    }
}