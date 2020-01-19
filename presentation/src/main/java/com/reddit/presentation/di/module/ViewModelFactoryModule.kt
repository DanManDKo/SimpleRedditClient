package com.reddit.presentation.di.module

import androidx.lifecycle.ViewModelProvider
import com.reddit.presentation.common.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 19:53
 */
@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}