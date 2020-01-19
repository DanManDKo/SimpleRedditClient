package com.reddit.data.network

import com.reddit.domain.exception.APIException
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 17:18
 */

class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {
        fun create(): RxErrorHandlingCallAdapterFactory {
            return RxErrorHandlingCallAdapterFactory()
        }
    }

    private val original: CallAdapter.Factory

    init {
        this.original = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit)
            : CallAdapter<*, *> {
        val wrappedAdapter = original.get(returnType, annotations, retrofit)
                as CallAdapter<out Any, *>
        return RxCallAdapterWrapper(wrappedAdapter)
    }

    private class RxCallAdapterWrapper<R> constructor(private val wrappedAdapter: CallAdapter<R, *>) :
        CallAdapter<R, Any> {

        override fun responseType(): Type {
            return wrappedAdapter.responseType()
        }

        override fun adapt(call: Call<R>): Any {
            var observable = wrappedAdapter.adapt(call)

            when (observable) {
                is Completable -> observable = observable
                    .onErrorResumeNext { throwable ->
                        Completable.error(mapError(throwable, call))
                    }
                is Flowable<*> -> observable = observable
                    .onErrorResumeNext(Function {
                        Flowable.error(mapError(it, call))
                    })
                is Single<*> -> observable = observable
                    .onErrorResumeNext { throwable ->
                        Single.error(mapError(throwable, call))
                    }
                is Observable<*> -> observable = observable
                    .onErrorResumeNext(Function {
                        Observable.error(mapError(it, call))
                    })
            }

            return observable
        }

        private fun mapError(throwable: Throwable, call: Call<R>): Throwable {
            if (throwable is HttpException) {
                val code: Int = throwable.code()

                return if (520 == code) {
                    throwable
                } else {
                    APIException(code, throwable.message())
                }
            } else {
                return throwable
            }
        }
    }
}
