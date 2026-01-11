package com.criticaltechwork.bbcnews.data.remote

import com.criticaltechwork.bbcnews.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("sources") source: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}
