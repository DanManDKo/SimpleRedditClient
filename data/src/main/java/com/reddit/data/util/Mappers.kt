package com.reddit.data.util

import com.reddit.data.network.mapper.Mapper
import java.util.*

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 21:57
 */
object Mappers {
    fun <F, T> mapCollection(list: List<F>?, mapper: Mapper<F, T>): List<T> {
        return if (list == null) {
            Collections.emptyList()
        } else {
            val size = list.size
            val result = ArrayList<T>(size)

            for (i in 0 until size) {
                val map = mapper.map(list[i])
                if (map != null) {
                    result.add(map)
                }
            }

            result
        }
    }
}