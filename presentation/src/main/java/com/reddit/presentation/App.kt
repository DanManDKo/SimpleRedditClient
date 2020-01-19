package com.reddit.presentation

import androidx.multidex.MultiDexApplication
import com.evernote.android.state.StateSaver
import com.reddit.presentation.di.AppComponent
import timber.log.Timber

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 16:14
 */
abstract class App : MultiDexApplication() {

    abstract fun getInjector(): AppComponent

    override fun onCreate() {
        super.onCreate()

        getInjector().inject(this)

        initAndroidState()
        initTimber()
    }

    private fun initAndroidState() {
        StateSaver.setEnabledForAllActivitiesAndSupportFragments(this, true)
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}