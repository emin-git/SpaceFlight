package com.eminProject.network.api

import com.eminProject.model.news.dto.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("v4/articles/")
    suspend fun getNews(
        @Query("limit") limit :Int?,
        @Query("is_featured") is_featured :Boolean?,
        @Query("published_at_gte") published_at_gte :String?,
        @Query("published_at_lte") published_at_lte :String?,
        @Query("offset") offset :Int?,
        @Query("ordering") ordering :List<String>?= null,
        @Query("search") search :String?
    ) : NewsDto
}