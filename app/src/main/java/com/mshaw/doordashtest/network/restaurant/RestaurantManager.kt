package com.mshaw.doordashtest.network.restaurant

import com.mshaw.doordashtest.models.RestaurantDetailsResponse
import com.mshaw.doordashtest.models.RestaurantListResponse
import com.mshaw.doordashtest.util.state.AwaitResult
import com.mshaw.doordashtest.util.extensions.awaitResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RestaurantManager @Inject constructor(private val service: RestaurantService) {
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