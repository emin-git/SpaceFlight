package com.eminProject.model.news.dto

import com.eminProject.model.news.Author
import kotlinx.serialization.Serializable

@Serializable
data class NewsDetailArgs(
    val title: String?,
    val imageUrl: String?,
    val summary: String?,
    val authors: List<Author>?,
    val publishedAt: String?,
    val updatedAt: String?,
    val newsSite: String?,
    val url: String?
)

@Serializable
data class NewsDetail(val args: NewsDetailArgs)