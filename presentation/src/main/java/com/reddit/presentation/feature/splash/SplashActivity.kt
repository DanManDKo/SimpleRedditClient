package com.reddit.presentation.feature.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.reddit.presentation.feature.feed.FeedActivity

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 13:48
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = FeedActivity.getIntent(this)
        startActivity(intent)
        finish()
    }
}