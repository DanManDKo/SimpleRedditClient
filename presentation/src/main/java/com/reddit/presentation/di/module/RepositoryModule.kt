package com.reddit.presentation.di.module

import com.reddit.domain.exception.repository.RedditRepository
import com.reddit.domain.exception.repository.Repository
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
    internal fun provideRedditRepository(): RedditRepository {
        return provides.getValue(RedditRepository::class.java).get() as RedditRepository
    }

}