package com.reddit.presentation.di.module

import com.reddit.domain.repository.FeedRepository
import com.reddit.domain.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Provider

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 17:00
 */
@Module
class RepositoryModule(
    private val provides: Map<Class<out Repository>,
            @JvmSuppressWildcards Provider<Repository>>
) {

    @Provides
    internal fun provideRedditRepository(): FeedRepository {
        return provides.getValue(FeedRepository::class.java).get() as FeedRepository
    }

}