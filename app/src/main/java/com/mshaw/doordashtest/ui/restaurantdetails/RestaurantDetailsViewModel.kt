package com.mshaw.doordashtest.ui.restaurantdetails

import androidx.lifecycle.*
import com.mshaw.doordashtest.models.RestaurantDetailsResponse
import com.mshaw.doordashtest.network.restaurant.RestaurantManager
import com.mshaw.doordashtest.util.state.AwaitResult
import com.mshaw.doordashtest.util.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(private val restaurantManager: RestaurantManager
): ViewModel() {
    private val _restaurantDetailsLiveData: MutableLiveData<State<RestaurantDetailsResponse>> = MutableLiveData<State<RestaurantDetailsResponse>>()
    val restaurantDetailsLiveData: LiveData<State<RestaurantDetailsResponse>> get() = _restaurantDetailsLiveData

    val restaurantDetailsDBLiveData: MutableLiveData<RestaurantDetailsResponse> = MutableLiveData()

    fun getRestaurantDetails(id: String) {
        _restaurantDetailsLiveData.value = State.Loading
        viewModelScope.launch {
            when (val result = restaurantManager.getRestaurantDetailsList(id)) {
                is AwaitResult.Ok -> {
                    _restaurantDetailsLiveData.value = State.Success(result.value)
                    restaurantDetailsDBLiveData.value = result.value
                }
                is AwaitResult.Error -> {
                    _restaurantDetailsLiveData.value = State.Error(result.exception)
                }
            }
        }
    }
}