package com.eminProject.newsfeature.viewmodel

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eminProject.common.utils.Resource
import com.eminProject.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.eminProject.common.utils.OneTimeEvent
import com.eminProject.model.news.Result
import com.eminProject.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val pageSize = 40
    private var page = 1

    private val _newsUiState = MutableStateFlow(UiState<List<Result>>(data = emptyList()))
    val newsUiState: StateFlow<UiState<List<Result>>> get() = _newsUiState.asStateFlow()

    private val _isRequestInProgress = MutableStateFlow(false)
    val isRequestInProgress: StateFlow<Boolean> get() = _isRequestInProgress

    internal val _newsList = MutableStateFlow<List<Result>>(emptyList())
    val newsList: StateFlow<List<Result>> get() = _newsList.asStateFlow()

    private val _likedNews = mutableStateListOf<Result>()
    val likedNews: List<Result> get() = _likedNews

    init {
        getNews(ordering = null, isFeatured = null, published_at_gte = null, published_at_lte = null, search = null)
        loadLikedNews()
    }

    internal fun getNews(ordering: List<String>?,isFeatured: Boolean?,published_at_gte: String?,published_at_lte: String?, search: String?) {
        val currentLimit = page * pageSize
        val currentOffset = (page - 1) * pageSize
        getNewsUseCase.executeGetNews(currentLimit, currentOffset, isFeatured, published_at_gte, published_at_lte, ordering, search)
            .map { resource ->
                when (resource) {
                    is Resource.Loading -> UiState(data = null, loading = true)
                    is Resource.Success -> {
                        val newList = resource.data ?: emptyList()
                        _newsList.update { it + newList }
                        loadLikedNews()
                        UiState(data = newList) }
                    is Resource.Error -> UiState(data = null, error = OneTimeEvent(resource.error))
                    is Resource.Warning -> UiState(
                        data = _newsList.value,
                        warning = OneTimeEvent(resource.message)
                    )
                }
            }
            .onEach { state ->
                _newsUiState.update { state as UiState<List<Result>> }
                _isRequestInProgress.value = false
            }
            .catch { e ->
                _newsUiState.value = UiState(data = null, error = OneTimeEvent(e))
                _isRequestInProgress.value = false
            }
            .launchIn(viewModelScope)
    }

    fun loadMoreNews() {
        page++
        getNews(ordering = null, isFeatured = null, published_at_gte = null, published_at_lte = null, search = null)
    }

    fun onEvent(event: NewsEvent) {
        when (event) {
            is NewsEvent.Request -> {
                page = 1
                _newsList.value = emptyList()
                getNews(event.ordering, event.is_featured, event.published_at_gte, event.published_at_lte, event.search)
            }
        }
    }

    fun toggleLike(clickedNews: Result) {
        val updatedNewsList = _newsUiState.value.data?.map {
            if (it.id == clickedNews.id) {
                it.copy(isLiked = !it.isLiked)
            } else it
        }

        _newsUiState.update { currentState ->
            currentState.copy(data = updatedNewsList)
        }

        _likedNews.clear()
        updatedNewsList?.filter { it.isLiked }?.let { filteredList ->
            _likedNews.addAll(filteredList)
        }
    }



    fun saveLikedNews(news: Result) {
        val likedIds = sharedPreferences.getStringSet("liked_news", mutableSetOf()) ?: mutableSetOf()
        likedIds.add(news.id.toString())
        sharedPreferences.edit().putStringSet("liked_news", likedIds).apply()
    }

    fun removeLikedNews(news: Result) {
        val likedIds = sharedPreferences.getStringSet("liked_news", mutableSetOf()) ?: mutableSetOf()
        likedIds.remove(news.id.toString())
        sharedPreferences.edit().putStringSet("liked_news", likedIds).apply()
    }

    fun loadLikedNews() {
        val likedIds = sharedPreferences.getStringSet("liked_news", mutableSetOf()) ?: mutableSetOf()
        val likedNewsList = _newsList.value.filter { likedIds.contains(it.id.toString()) }

        _likedNews.clear()
        _likedNews.addAll(likedNewsList)

        _newsList.value.forEach { news ->
            news.isLiked = likedIds.contains(news.id.toString())
        }
    }
}