package com.reddit.data.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.reddit.data.BuildConfig
import com.reddit.data.network.RxErrorHandlingCallAdapterFactory
import com.reddit.data.network.interceptor.HeaderInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 17:13
 */
@Module
class NetworkModule {

    @Singleton
    @Provides
    internal fun createRetrofit(): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.SCHEME + BuildConfig.HOST)
            .addConverterFactory(GsonConverterFactory.create(createGson()))
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())

        builder.client(createOkHttpClient())

        return builder.build()
    }

    @Provides
    internal fun createGson(): Gson = GsonBuilder().create()!!

    @Provides
    internal fun createOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(HeaderInterceptor())
        clientBuilder.connectTimeout(15, TimeUnit.SECONDS)
        clientBuilder.readTimeout(15, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(15, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            clientBuilder.connectTimeout(10, TimeUnit.SECONDS)
            clientBuilder.readTimeout(10, TimeUnit.SECONDS)
            clientBuilder.writeTimeout(10, TimeUnit.SECONDS)
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(interceptor)
            clientBuilder.addInterceptor(OkHttpProfilerInterceptor())
        }

        return clientBuilder.build()
    }
}