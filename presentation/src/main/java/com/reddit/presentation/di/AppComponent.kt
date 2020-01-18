package com.reddit.presentation.di

import androidx.lifecycle.ViewModelProvider
import com.reddit.domain.exception.repository.Repository
import com.reddit.presentation.App
import com.reddit.presentation.di.module.AppModule
import com.reddit.presentation.di.module.PresentationModule
import com.reddit.presentation.di.module.RepositoryModule
import com.reddit.presentation.di.module.ViewModelModule
import dagger.Component
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 16:15
 */

@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        PresentationModule::class,
        RepositoryModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(app: App)

    fun getViewModelFactoryProvider(): ViewModelProvider.Factory

    object Initializer {
        fun init(
            app: App, provides: Map<Class<out Repository>,
                    @JvmSuppressWildcards Provider<Repository>>
        ): AppComponent {

            return DaggerAppComponent.builder()
                .repositoryModule(RepositoryModule(provides))
                .appModule(AppModule(app))
                .build()
        }
    }
}