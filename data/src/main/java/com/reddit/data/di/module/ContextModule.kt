package com.reddit.data.di.module

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 17:11
 */
@Module
class ContextModule(private val context: Context) {

    @Provides
    internal fun provideContext(): Context = context

}