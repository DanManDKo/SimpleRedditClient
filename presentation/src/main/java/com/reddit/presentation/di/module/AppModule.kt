package com.reddit.presentation.di.module

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 16:16
 */
@Module
class AppModule(private val context: Context) {
    @Provides
    internal fun provideContext(): Context = context
}