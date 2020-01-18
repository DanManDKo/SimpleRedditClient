package com.reddit.simpleredditclient

import android.content.Context
import com.reddit.data.di.DataComponent
import com.reddit.presentation.App
import com.reddit.presentation.di.AppComponent

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 16:08
 */
class InjectorApp : App() {
    private val appComponent: AppComponent by lazy {
        val dataComponent = DataComponent.Initializer
            .init(this as Context)

        AppComponent.Initializer
            .init(this, dataComponent.getRepositories())
    }

    override fun getInjector(): AppComponent {
        return appComponent
    }
}