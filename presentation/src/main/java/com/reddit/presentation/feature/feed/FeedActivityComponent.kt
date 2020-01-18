package com.reddit.presentation.feature.feed

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.reddit.presentation.App
import com.reddit.presentation.di.ActivityScope
import com.reddit.presentation.di.AppComponent
import com.reddit.presentation.di.ViewModelKey
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 19:18
 */
@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        FeedActivityComponent.ActivityModule::class,
        FeedActivityComponent.ViewModelModule::class
    ]
)
interface FeedActivityComponent {
    fun inject(activity: FeedActivity)

    @Module
    class ActivityModule(private val activity: FeedActivity) {
        @Provides
        fun provideActivity(): Activity = activity
    }

    @Module
    abstract class ViewModelModule {
        @Binds
        @IntoMap
        @ViewModelKey(FeedViewModel::class)
        abstract fun bindFetchUserViewModel(viewModel: FeedViewModel): ViewModel
    }

    object Initializer {
        fun init(activity: FeedActivity): FeedActivityComponent {
            return DaggerFeedActivityComponent.builder()
                .activityModule(ActivityModule(activity))
                .appComponent((activity.application as App).getInjector())
                .build()
        }
    }
}