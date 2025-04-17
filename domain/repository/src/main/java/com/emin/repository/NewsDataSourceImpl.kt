package com.emin.repository

import com.emin.common.di.IoDispatcher
import com.emin.model.news.dto.NewsDto
import com.emin.network.api.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : NewsDataSource {

    override suspend fun getNews(limit: Int?, is_featured: Boolean?, published_at_gte: String?, published_at_lte: String?, offset: Int?, ordering: List<String>?, search: String?): NewsDto {
        return withContext(ioDispatcher){
            apiService.getNews(limit = limit, is_featured = is_featured, published_at_gte = published_at_gte, published_at_lte = published_at_lte, offset = offset, ordering = ordering, search = search)
        }
    }
}