package com.reddit.presentation.feature.feed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.reddit.presentation.R
import com.reddit.presentation.common.BaseActivity
import com.reddit.presentation.databinding.ActivityFeedBinding

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 19:03
 */
class FeedActivity : BaseActivity<ActivityFeedBinding, FeedViewModel>(
    R.layout.activity_feed,
    FeedViewModel::class.java
) {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, FeedActivity::class.java)
        }
    }

    private val component by lazy { FeedActivityComponent.Initializer.init(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding.vm = vm
    }
}