package com.reddit.presentation.feature.feed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
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

    override fun inject(fragment: Fragment) {
        if (fragment is FeedFragment) {
            component.inject(fragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = PagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    inner class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> FeedFragment.newInstance(FeedType.MAIN)
                1 -> FeedFragment.newInstance(FeedType.FAVORITE)
                else -> throw IllegalArgumentException("Unknown tab $position")
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> getString(R.string.feed_label_feed)
                1 -> getString(R.string.feed_label_favorites)
                else -> throw IllegalArgumentException("Unknown tab $position")
            }
        }

        override fun getCount(): Int = 2
    }
}