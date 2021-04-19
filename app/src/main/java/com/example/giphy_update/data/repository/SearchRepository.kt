package com.example.giphy_update.data.repository

import com.example.giphy_update.api.NaverApis
import com.example.giphy_update.data.model.MovieData
import com.example.giphy_update.data.model.NaverQueryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

interface SearchRepository {
    suspend fun getMovieList(
        query: String,
        start: Int,
        display: Int
    ): Response<NaverQueryResponse<MovieData>>

}


class SearchRepositoryImpl @Inject constructor(
    private val naverApis: NaverApis
) : SearchRepository {

    override suspend fun getMovieList(
        query: String,
        start: Int,
        display: Int
    ): Response<NaverQueryResponse<MovieData>> {
        return withContext(Dispatchers.IO) {
            naverApis.getMovieList(
                query, start, display
            )
        }
    }

}