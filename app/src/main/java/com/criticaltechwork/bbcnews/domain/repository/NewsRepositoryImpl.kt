package com.criticaltechwork.bbcnews.domain.repository

import com.criticaltechwork.bbcnews.BuildConfig
import com.criticaltechwork.bbcnews.data.model.NewsResponse
import com.criticaltechwork.bbcnews.data.remote.NewsApi
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
) : NewsRepository {

    private val apiKey = BuildConfig.API_KEY
    private val sourceId = BuildConfig.NEWS_SOURCE_ID

    override suspend fun getNews(): Result<List<NewsResponse.Article>> {
        return try {
            val response = api.getTopHeadlines(sourceId, apiKey)

            if (response.status == "ok") {
                val sortedList = response.articles?.sortedByDescending { it.publishedAt } ?: emptyList()
                Result.success(sortedList)
            } else {
                Result.failure(Exception("API Error: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}