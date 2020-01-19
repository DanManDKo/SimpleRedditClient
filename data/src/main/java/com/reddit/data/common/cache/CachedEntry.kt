package com.reddit.data.common.cache

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 20:58
 */
class CachedEntry<E> internal constructor(
    val entry: E,
    private val createTime: Long)
    : Cache {

    init {
        assert(entry != null)
    }

    override fun getCreateTime(): Long {
        return createTime
    }
}