package com.reddit.data.common.cache

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Predicate
import java.util.concurrent.TimeUnit

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 20:56
 */
class CachePolicy private constructor(private val time: Int, private val timeUnit: TimeUnit) :
    Predicate<Cache> {

    override fun test(entry: Cache): Boolean {
        val currentTime = getTime()
        return currentTime - entry.getCreateTime() < timeUnit.toMillis(time.toLong())
    }

    companion object {

        fun create(time: Int, timeUnit: TimeUnit): CachePolicy {
            return CachePolicy(time, timeUnit)
        }

        fun infinite(): CachePolicy {
            return CachePolicy(Integer.MAX_VALUE, TimeUnit.DAYS)
        }

        fun <T> createEntry(@NonNull t: T): CachedEntry<T> {
            return CachedEntry(t, getTime())
        }

        fun createEntry(): CacheInfo {
            return CacheInfo(getTime())
        }

        private fun getTime(): Long {
            return System.currentTimeMillis()
        }
    }
}