package com.eminProject.newsfeature
/*
import android.content.SharedPreferences
import com.eminProject.newsfeature.viewmodel.NewsViewModel
import com.eminProject.usecase.GetNewsUseCase
import com.eminProject.model.news.Result
import com.eminProject.common.utils.Resource
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class NewsViewModelTest {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var getNewsUseCase: GetNewsUseCase
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        getNewsUseCase = mockk()
        sharedPreferences = mockk(relaxed = true)
        sharedPreferencesEditor = mockk(relaxed = true)

        newsViewModel = NewsViewModel(getNewsUseCase, sharedPreferences)

        every { sharedPreferences.edit() } returns sharedPreferencesEditor
    }

    @Test
    fun `test getNews successful`() = runTest {
        val fakeNews = listOf(Result(id = 1, title = "News 1", isLiked = false))
        val resource = Resource.Success(fakeNews)

        coEvery {
            getNewsUseCase.executeGetNews(
                any<Int>(), any<Int>(), any<Boolean>(), any<String>(),
                any<String>(), any<List<String>>(), any<String>()
            )
        } returns flowOf(resource)

        newsViewModel.getNews(ordering = null, isFeatured = null, published_at_gte = null, published_at_lte = null, search = null)

        val uiState = newsViewModel.newsUiState.value
        assertNotNull(uiState.data)
        assertEquals(fakeNews, uiState.data)
        assertFalse(uiState.loading)
        assertNull(uiState.error.peekContent())
    }

    @Test
    fun `test toggleLike adds liked news`() {
        val news = Result(id = 1, title = "Test News", isLiked = false)
        newsViewModel.toggleLike(news)

        assertEquals(1, newsViewModel.likedNews.size)
        assertTrue(newsViewModel.likedNews.contains(news.copy(isLiked = true)))
    }

    @Test
    fun `test saveLikedNews saves liked news to shared preferences`() {
        val news = Result(id = 1, title = "Test News", isLiked = true)

        newsViewModel.saveLikedNews(news)

        val likedIds = mutableSetOf("1")
        verify { sharedPreferences.edit().putStringSet("liked_news", likedIds).apply() }
    }

    @Test
    fun `test loadLikedNews loads liked news from shared preferences`() {
        val likedIds = setOf("1")
        val likedNews = listOf(Result(id = 1, title = "Liked News", isLiked = true))
        val newsList = listOf(
            Result(id = 1, title = "Liked News", isLiked = false),
            Result(id = 2, title = "Unliked News", isLiked = false)
        )

        every { sharedPreferences.getStringSet("liked_news", any()) } returns likedIds
        newsViewModel._newsList.value = newsList

        newsViewModel.loadLikedNews()

        assertTrue(newsViewModel.likedNews.contains(likedNews[0]))
        assertTrue(newsViewModel.newsList.value[0].isLiked)
    }
}*/