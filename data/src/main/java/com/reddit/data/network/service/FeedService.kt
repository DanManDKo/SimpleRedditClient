package com.reddit.data.network.service

import com.reddit.data.network.dto.FeedDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 17:23
 */
interface FeedService {

    @GET("r/all/top.json")
    fun getTopList(
        @Query("count") count: Int,
        @Query("limit") limit: Int,
        @Query("t") period: String,
        @Query("after") after: String?
    ): Single<FeedDto>
}