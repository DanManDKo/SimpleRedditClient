package com.reddit.data.di.module

import com.reddit.data.network.service.RedditService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 17:22
 */
@Module
class ServiceModule {
    @Provides
    @Singleton
    internal fun provideDiscoverService(
        retrofit: Retrofit
    ): RedditService {
        return retrofit.create(RedditService::class.java)
    }
}