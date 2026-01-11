package com.criticaltechwork.bbcnews.domain.repo

import com.criticaltechwork.bbcnews.data.model.NewsResponse
import com.criticaltechwork.bbcnews.data.remote.NewsApi
import com.criticaltechwork.bbcnews.domain.repository.NewsRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NewsRepositoryImplTest {

    private val api = mockk<NewsApi>()
    private lateinit var repository: NewsRepositoryImpl

    @Before
    fun setup() {
        repository = NewsRepositoryImpl(api)
    }

    @Test
    fun `getNews should fail if headlines are not sorted newest first`() = runTest {
        val older = createArticle("Older News", "2025-01-01T10:00:00Z")
        val newer = createArticle("Newer News", "2026-01-10T10:00:00Z")

        val mockResponse = NewsResponse(
            status = "ok",
            totalResults = 2,
            articles = listOf(older, newer)
        )

        coEvery { api.getTopHeadlines(any(), any()) } returns mockResponse
        val result = repository.getNews().getOrNull()

        Assert.assertEquals("Newer News", result?.get(0)?.title)
        Assert.assertEquals("Older News", result?.get(1)?.title)
    }

    private fun createArticle(title: String, date: String) = NewsResponse.Article(
        source = NewsResponse.Article.Source("id", "name"),
        author = null,
        title = title,
        description = null,
        url = "",
        urlToImage = null,
        publishedAt = date,
        content = null
    )
}