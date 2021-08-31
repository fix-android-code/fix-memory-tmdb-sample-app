package com.example.suki.common.data.remote.api

import com.example.suki.common.util.Constant.API_KEY
import com.example.suki.movie.filter.data.remote.response.MovieFilterResponseNetworkDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppApi {

    @GET("movie/{filter}")
    suspend fun getMovieFilterApi(
        @Path("filter") filter: String,
        @Query("api_key") key: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ): MovieFilterResponseNetworkDto
}