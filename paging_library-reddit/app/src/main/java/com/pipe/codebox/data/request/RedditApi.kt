package com.pipe.codebox.data.request

import com.pipe.codebox.data.models.ServerData
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {

    @GET("hot.json")
    suspend fun getData(
        @Query("after") after: String,
        @Query("limit") loadSize: Int = 0
    ): ServerData
}
