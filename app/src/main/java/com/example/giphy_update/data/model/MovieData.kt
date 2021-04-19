package com.example.giphy_update.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieData(
    val id: Long? = null,
    val title: String,
    val link: String,
    val image: String,
    val subtitle: String,
    val pubDate: String,
    val director: String,
    val actor: String,
    val userRating: Float,
    var serialTest : String = "dksush test"
)