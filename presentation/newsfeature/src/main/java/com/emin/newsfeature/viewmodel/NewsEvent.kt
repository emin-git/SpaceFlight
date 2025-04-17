package com.emin.newsfeature.viewmodel

sealed class NewsEvent {
    data class Request(
        val limit: Int?,
        val ordering: List<String>?,
        val search: String?,
        val is_featured: Boolean?,
        val published_at_gte: String?,
        val published_at_lte: String?
    ): NewsEvent()
}