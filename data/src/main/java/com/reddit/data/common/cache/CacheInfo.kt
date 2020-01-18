package com.reddit.data.common.cache

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 20:58
 */
class CacheInfo
internal constructor(private val createTime: Long)
    : Cache {

    override fun getCreateTime(): Long {
        return createTime
    }
}