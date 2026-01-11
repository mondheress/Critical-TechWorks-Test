package com.criticaltechwork.bbcnews.domain.usecase

import com.criticaltechwork.bbcnews.data.model.NewsResponse
import com.criticaltechwork.bbcnews.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): Result<List<NewsResponse.Article>> {
        return repository.getNews()
    }
}