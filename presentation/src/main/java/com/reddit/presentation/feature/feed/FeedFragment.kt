package com.reddit.presentation.feature.feed

import android.os.Bundle
import com.reddit.presentation.R
import com.reddit.presentation.common.BaseFragment
import com.reddit.presentation.databinding.FragmentFeedBinding

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-19
 * Time: 13:05
 */
class FeedFragment private constructor() : BaseFragment<FragmentFeedBinding, FeedViewModel>(
    R.layout.fragment_feed,
    FeedViewModel::class.java
) {
    companion object {

        private const val KEY_TYPE = "key_type"

        fun newInstance(type: FeedType): FeedFragment {
            val bundle = Bundle().apply {
                putSerializable(KEY_TYPE, type)
            }
            return FeedFragment().apply {
                arguments = bundle
            }
        }
    }

    private val feedType: FeedType by lazy {
        arguments!!.getSerializable(KEY_TYPE) as FeedType
    }

    override fun configureView() {
        vm.feedType.value = feedType
    }

    private fun observe() {

    }

    private fun initRecycler() {

    }
}