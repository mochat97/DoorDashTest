package com.mshaw.doordashtest.network.restaurantlist

import com.mshaw.doordashtest.models.RestaurantDetailsResponse
import com.mshaw.doordashtest.models.RestaurantListResponse
import com.mshaw.doordashtest.util.AwaitResult
import com.mshaw.doordashtest.util.extensions.awaitResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RestaurantListManager @Inject constructor(private val service: RestaurantListService) {
    suspend fun getRestaurantList(lat: Double,
                                  lng: Double,
                                  offset: Int,
                                  limit: Int): AwaitResult<RestaurantListResponse> {
        return withContext(Dispatchers.IO) { service.getRestaurantList(lat, lng, offset, limit).awaitResult() }
    }

    suspend fun getRestaurantDetailsList(id: String): AwaitResult<RestaurantDetailsResponse> {
        return withContext(Dispatchers.IO) { service.getRestaurantDetail(id).awaitResult() }
    }
}