package com.criticaltechwork.bbcnews.domain.usecase

import com.criticaltechwork.bbcnews.data.model.NewsResponse
import com.criticaltechwork.bbcnews.domain.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetNewsUseCaseTest {

    private val repository = mockk<NewsRepository>()
    private lateinit var getNewsUseCase: GetNewsUseCase

    @Before
    fun setup() {
        getNewsUseCase = GetNewsUseCase(repository)
    }

    @Test
    fun `should return success when repository fetches data`() = runTest {
        val mockArticles = listOf(createMockArticle("Success Test"))
        coEvery { repository.getNews() } returns Result.success(mockArticles)

        val result = getNewsUseCase().getOrNull()

        Assert.assertEquals(1, result?.size)
        Assert.assertEquals("Success Test", result?.get(0)?.title)
    }

    @Test
    fun `should return failure when repository fails`() = runTest {
        val exception = Exception("Network Error")
        coEvery { repository.getNews() } returns Result.failure(exception)

        val result = getNewsUseCase()

        Assert.assertTrue(result.isFailure)
        Assert.assertEquals("Network Error", result.exceptionOrNull()?.message)
    }

    private fun createMockArticle(title: String) = NewsResponse.Article(
        source = NewsResponse.Article.Source("id", "name"),
        author = null, title = title, description = null,
        url = "", urlToImage = null, publishedAt = "2026-01-11", content = null
    )
}