package com.reddit.data.feature.feed

import androidx.room.*
import com.reddit.domain.model.Feed
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-19
 * Time: 18:12
 */
@Dao
abstract class FeedDao {
    companion object {
        const val TABLE_NAME = "feed"
    }

    @Query("SELECT * FROM $TABLE_NAME")
    abstract fun observeFeed(): Observable<List<Feed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun storeFeed(feed: Feed): Completable

    @Delete
    abstract fun remove(feed: Feed): Completable
}