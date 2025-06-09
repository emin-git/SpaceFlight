package com.eminProject.repository

import com.eminProject.model.news.dto.NewsDto

interface NewsDataSource {

    suspend fun getNews(limit: Int? = null,is_featured: Boolean? = null, published_at_gte: String? = null, published_at_lte: String? = null, offset: Int? = null, ordering: List<String>? = null, search: String? = null) : NewsDto

}