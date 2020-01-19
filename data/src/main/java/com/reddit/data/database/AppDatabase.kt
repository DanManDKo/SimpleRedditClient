package com.reddit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.reddit.data.feature.feed.FeedDao
import com.reddit.domain.model.Feed

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-19
 * Time: 18:08
 */
@Database(entities = [Feed::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getFeedDao(): FeedDao

}