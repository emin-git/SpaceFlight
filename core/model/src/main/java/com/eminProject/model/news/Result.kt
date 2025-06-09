package com.eminProject.model.news

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val id: Int? = null,
    val title: String? = null,
    val authors: List<Author>? = null,
    val url: String? = null,
    val image_url: String? = null,
    val news_site: String? = null,
    val summary: String? = null,
    val published_at: String? = null,
    val updated_at: String? = null,
    val featured: Boolean? = null,
    val launches: List<Launch>? = null,
    val events: List<Event>? = null,
    var isLiked: Boolean = false
)