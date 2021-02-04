package com.mshaw.doordashtest.ui.restaurantinfo

import androidx.lifecycle.ViewModel
import com.mshaw.doordashtest.models.Store

class RestaurantInfoViewModel(val store: Store): ViewModel() {
    val deliveryFeeFormatted: String
        get() = "Delivery fee: ${store.deliveryFeeMonetaryFields.displayString}"
}