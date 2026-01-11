package com.criticaltechwork.bbcnews.domain.repository

import com.criticaltechwork.bbcnews.data.model.NewsResponse


interface NewsRepository {
    suspend fun getNews(): Result<List<NewsResponse.Article>>
}
