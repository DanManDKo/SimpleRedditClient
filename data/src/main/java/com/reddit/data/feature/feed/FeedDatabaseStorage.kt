package com.reddit.data.feature.feed

import com.reddit.data.database.DatabaseManager
import com.reddit.domain.model.Feed
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-19
 * Time: 18:21
 */
class FeedDatabaseStorage
@Inject
constructor(private val databaseManager: DatabaseManager) {
    private val observable: Observable<FeedDao> = databaseManager.getDb()
        .map {
            it.getFeedDao()
        }
        .replay(1)
        .refCount()
        .subscribeOn(Schedulers.io())

    fun observeFeed(): Observable<List<Feed>> {
        return observable.concatMap { dao ->
            dao.observeFeed()
        }
    }

    fun storeFeed(feed: Feed): Completable {
        return observable.take(1)
            .concatMapCompletable { dao ->
                dao.storeFeed(feed)
            }
    }

    fun removeFeed(feed: Feed): Completable {
        return observable.take(1).concatMapCompletable { dao ->
            dao.remove(feed)
        }
    }

}