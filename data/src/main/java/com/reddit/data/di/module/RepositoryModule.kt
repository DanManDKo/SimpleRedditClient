package com.reddit.data.di.module

import com.reddit.data.di.RepositoryKey
import com.reddit.data.network.RedditRepositoryImpl
import com.reddit.domain.repository.FeedRepository
import com.reddit.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 17:24
 */
@Module
abstract class RepositoryModule {

    @Binds
    @IntoMap
    @RepositoryKey(FeedRepository::class)
    abstract fun provideMovieRepository(repository: RedditRepositoryImpl): Repository
}