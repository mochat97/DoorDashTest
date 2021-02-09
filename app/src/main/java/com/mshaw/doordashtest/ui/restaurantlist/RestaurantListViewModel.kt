package com.mshaw.doordashtest.ui.restaurantlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshaw.doordashtest.models.RestaurantListResponse
import com.mshaw.doordashtest.network.restaurant.RestaurantManager
import com.mshaw.doordashtest.util.state.AwaitResult
import com.mshaw.doordashtest.util.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantListViewModel @Inject constructor(private val restaurantManager: RestaurantManager): ViewModel() {
    private val _restaurantListLiveData: MutableLiveData<State<RestaurantListResponse>> = MutableLiveData()
    val restaurantListLiveData: LiveData<State<RestaurantListResponse>> get() = _restaurantListLiveData

    fun fetchRestaurantList(lat: Double, lng: Double, offset: Int, limit: Int) {
        _restaurantListLiveData.value = State.Loading
        viewModelScope.launch {
            when (val result = restaurantManager.getRestaurantList(lat, lng, offset, limit)) {
                is AwaitResult.Ok -> {
                    _restaurantListLiveData.value = State.Success(result.value)
                }
                is AwaitResult.Error -> {
                    _restaurantListLiveData.value = State.Error(result.exception)
                }
            }
        }
    }
}