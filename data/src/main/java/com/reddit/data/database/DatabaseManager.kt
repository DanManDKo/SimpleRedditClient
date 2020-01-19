package com.reddit.data.database

import android.content.Context
import androidx.room.Room
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-19
 * Time: 18:19
 */
@Singleton
class DatabaseManager
@Inject
constructor(private val context: Context) {

    private val observable = Observable
        .create { emitter: ObservableEmitter<AppDatabase> ->
            val db = Room
                .databaseBuilder(context, AppDatabase::class.java, "main.db")
                .fallbackToDestructiveMigration()
                .build()
            Timber.d("open")
            emitter.setCancellable {
                if (!emitter.isDisposed) {
                    db.close()
                    Timber.d("close")
                }
            }
            if (!emitter.isDisposed) {
                emitter.onNext(db)
            }
        }.replay(1).refCount()

    fun getDb(): Observable<AppDatabase> {
        return observable
    }

    fun clean(): Completable {
        return observable.map { it.clearAllTables() }.take(1).ignoreElements()
    }
}