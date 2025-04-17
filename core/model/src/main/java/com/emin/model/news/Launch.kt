package com.emin.model.news
import kotlinx.serialization.Serializable

@Serializable
data class Launch(
    val launch_id: String? = null,
    val provider: String? = null
)