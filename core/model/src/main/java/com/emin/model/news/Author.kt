package com.emin.model.news

import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val name: String? = null,
    val socials: Socials? = null
)