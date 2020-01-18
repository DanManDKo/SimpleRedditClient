package com.reddit.data.network.mapper.request

import com.reddit.data.network.mapper.Mapper
import com.reddit.domain.type.FeedPeriod
import javax.inject.Inject

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 21:18
 */
class PeriodRequestMapper
@Inject
constructor() : Mapper<FeedPeriod, String> {
    override fun map(value: FeedPeriod): String {
        return when (value) {
            FeedPeriod.HOUR -> "hour"
            FeedPeriod.DAY -> "day"
            FeedPeriod.WEEK -> "week"
            FeedPeriod.MONTH -> "month"
            FeedPeriod.YEAR -> "year"
            FeedPeriod.ALL -> "all"
        }
    }
}