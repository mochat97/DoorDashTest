package com.mshaw.doordashtest.ui.restaurantlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshaw.doordashtest.models.Store
import com.mshaw.doordashtest.network.restaurantlist.RestaurantListManager
import com.mshaw.doordashtest.util.AwaitResult
import com.mshaw.doordashtest.util.extensions.awaitResult
import com.mshaw.doordashtest.util.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantListViewModel @Inject constructor(private val restaurantListManager: RestaurantListManager): ViewModel() {
    private val _restaurantListLiveData: MutableLiveData<State<List<Store>>> = MutableLiveData()
    val restaurantListLiveData: LiveData<State<List<Store>>> get() = _restaurantListLiveData

    fun fetchRestaurantList(lat: Double, lng: Double, offset: Int, limit: Int) {
        _restaurantListLiveData.value = State.Loading
        viewModelScope.launch {
            when (val result = restaurantListManager.getRestaurantList(lat, lng, offset, limit)) {
                is AwaitResult.Ok -> {
                    _restaurantListLiveData.value = State.Success(result.value.stores)
                }
                is AwaitResult.Error -> {
                    _restaurantListLiveData.value = State.Error(result.exception)
                }
            }
        }
    }
}