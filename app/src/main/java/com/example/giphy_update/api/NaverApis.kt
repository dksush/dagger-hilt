package com.example.giphy_update.api

import com.example.giphy_update.data.model.MovieData
import com.example.giphy_update.data.model.NaverQueryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApis {

    companion object {
        const val BaseUrl: String = "https://openapi.naver.com/"
    }

    @GET("v1/search/movie.json")
    suspend fun getMovieList(
        @Query("query") query: String,
        @Query("start") start: Int,
        @Query("display") display: Int
    ): Response<NaverQueryResponse<MovieData>>



    @GET("v1/search/movie.json")
    suspend fun getMovieList11(
        @Query("query") query: String,
        @Query("start") start: Int,
        @Query("display") display: Int
    ): Response<NaverQueryResponse<MovieData>>
}