package com.emin.model.news
import kotlinx.serialization.Serializable

@Serializable
data class Socials(
    val x: String?,
    val youtube: String? = null,
    val instagram: String? = null,
    val linkedin: String? = null,
    val mastodon: String? = null,
    val bluesky: String? = null
)