package com.freshworks.freshworksandroidgiphy.network

import com.freshworks.freshworksandroidgiphy.network.model.TrendingGifsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("trending")
    fun getTrendingGifs(@Query("api_key") apiKey: String, @Query("offset") offSet: Int, @Query("limit") limit: Int): Call<TrendingGifsModel>

    @GET("search")
    fun searchTrendingGifs(@Query("api_key") apiKey: String, @Query("q") searchText: String, @Query("offset") offSet: Int, @Query("limit") limit: Int): Call<TrendingGifsModel>

}