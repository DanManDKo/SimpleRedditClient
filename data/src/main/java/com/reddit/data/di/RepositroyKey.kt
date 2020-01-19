package com.reddit.data.di

import com.reddit.domain.repository.Repository
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 17:25
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class RepositoryKey(val value: KClass<out Repository>)