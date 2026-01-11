package com.criticaltechwork.bbcnews.presentation.main

import com.criticaltechwork.bbcnews.data.model.NewsResponse
import com.criticaltechwork.bbcnews.domain.usecase.GetNewsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    private val getNewsUseCase = mockk<GetNewsUseCase>()
    private lateinit var viewModel: MainViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(getNewsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchNews success should update list and hide loading`() = runTest {
        val mockArticles = listOf(
            NewsResponse.Article(title = "Headline 1", publishedAt = "2026-01-11", source = NewsResponse.Article.Source("id", "name"), author = null, description = null, url = "", urlToImage = null, content = null)
        )
        coEvery { getNewsUseCase() } returns Result.success(mockArticles)

        viewModel.fetchNews()

        assertEquals(mockArticles, viewModel.articles.value)
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `fetchNews failure should show error and hide loading`() = runTest {
        val errorMsg = "No Internet"
        coEvery { getNewsUseCase() } returns Result.failure(Exception(errorMsg))
        viewModel.fetchNews()
        assertEquals(errorMsg, viewModel.errorMessage.value)
        assertEquals(false, viewModel.isLoading.value)
    }
}