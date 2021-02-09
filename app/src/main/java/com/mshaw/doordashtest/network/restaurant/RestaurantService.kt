package com.mshaw.doordashtest.network.restaurant

import com.mshaw.doordashtest.models.RestaurantDetailsResponse
import com.mshaw.doordashtest.models.RestaurantListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestaurantService {
    @GET("/v1/store_feed/")
    suspend fun getRestaurantList(@Query("lat") lat: Double,
                                  @Query("lng") lng: Double,
                                  @Query("offset") offset: Int,
                                  @Query("limit") limit: Int): Response<RestaurantListResponse>

    @GET("/v2/restaurant/{id}/")
    suspend fun getRestaurantDetail(@Path("id") id: String): Response<RestaurantDetailsResponse>
}