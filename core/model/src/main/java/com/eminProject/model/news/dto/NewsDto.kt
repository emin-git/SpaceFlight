package com.eminProject.model.news.dto

import com.eminProject.model.news.Result

data class NewsDto(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<Result>? = null
)

fun NewsDto.toNewsList(): List<Result>? {
    return results?.map {
        Result(
            it.id,
            it.title,
            it.authors,
            it.url,
            it.image_url,
            it.news_site,
            it.summary,
            it.published_at,
            it.updated_at,
            it.featured,
            it.launches,
            it.events
        )
    }
}