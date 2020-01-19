package com.reddit.presentation.di.module

import com.reddit.presentation.common.error.DefaultErrorHandler
import com.reddit.presentation.common.error.ErrorHandler
import dagger.Module
import dagger.Provides

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 16:26
 */
@Module
class PresentationModule {

    @Provides
    internal fun provideDefaultError(errorHandler: DefaultErrorHandler)
            : ErrorHandler = errorHandler
}
