package com.emin.model.news
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val event_id: Int? = null,
    val provider: String? = null
)