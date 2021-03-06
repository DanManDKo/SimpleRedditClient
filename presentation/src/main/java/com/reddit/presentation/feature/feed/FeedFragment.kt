package com.reddit.presentation.feature.feed

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.reddit.domain.model.Feed
import com.reddit.presentation.BuildConfig
import com.reddit.presentation.R
import com.reddit.presentation.common.BaseFragment
import com.reddit.presentation.common.extensions.defaultObserve
import com.reddit.presentation.common.recycler.LoadingAdapter
import com.reddit.presentation.databinding.FragmentFeedBinding
import com.reddit.presentation.utils.ThemeUtils


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

    private lateinit var adapter: FeedAdapter
    private lateinit var loadingAdapter: LoadingAdapter

    override fun configureView() {
        vm.feedType.value = feedType
        initRecycler()
        observe()
    }

    private fun initRecycler() {
        adapter = FeedAdapter(vm)
        loadingAdapter = LoadingAdapter(adapter) {
            vm.loadMore()
        }
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = loadingAdapter

    }

    private fun observe() {
        vm.hasNext.defaultObserve(this) {
            loadingAdapter.setHasNext(it!!)
        }
        vm.loading.defaultObserve(this) {
            loadingAdapter.setLoading(it!!)
        }
        vm.content.defaultObserve(this) {
            adapter.submitList(it)
        }
        vm.feedClickedEvent.defaultObserve(this) {
            showFeedDetail(it)
        }
    }

    private fun showFeedDetail(feed: Feed) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .addDefaultShareMenuItem()
            .setToolbarColor(
                ThemeUtils.getColorFromAttr(context!!, R.attr.colorPrimary)
            )
            .setShowTitle(true)
            .build()
        val uri = BuildConfig.SCHEME + BuildConfig.HOST + feed.permalink
        customTabsIntent.launchUrl(context, Uri.parse(uri))
    }

}