package com.example.giphy_update.data.model

import kotlinx.serialization.Serializable


@Serializable
data class NaverQueryResponse<T>(
    val items: List<T>
)